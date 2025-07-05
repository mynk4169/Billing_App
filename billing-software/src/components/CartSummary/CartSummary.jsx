import { useContext, useState } from 'react';
import './CartSummary.css';
import { AppContext } from '../../context/AppContext';
import ReceiptPopup from '../ReceiptPopup/ReceiptPopup';
import { createOrder, deleteOrder } from '../../service/OrderService';
import toast from 'react-hot-toast';
import { createRazorpayOrder, verifyPayment } from '../../service/PaymentService';
import { AppConstants } from '../../utils/Constants';

const CartSummary = ({customerName,mobileNumber,setCustomerName,setMobileNumber})=>
{
    const{cartItems,clearCart} = useContext(AppContext);
    const [isProcessing,setIsProcessing]=useState(false);
    const [orderDetails,setOrderDetails]= useState(null);
    const [showPopup,setShowPopup] = useState(false);
    const totalAmount = cartItems.reduce((total,item)=>total+item.price *item.quantity,0);
    const tax = totalAmount *0.01;
    const grandTotal = totalAmount + tax;
console.log(customerName);
console.log(mobileNumber);
console.log(customerName,"type",typeof customerName);
console.log(mobileNumber,"Type",typeof mobileNumber);
    const clearAll = () =>
    {
        setCustomerName("");
        setMobileNumber("");
        clearCart();
    }
    const placeOrder = ()=>
    {
        setShowPopup(true);
        clearAll();
    }
    const handlerPrintReceipt = () =>
    {
        window.print();
    }
    const loadRazorpayScript = () =>
    {
        return new Promise((resolve,reject)=>
        {
            const script = document.createElement('script');
            script.src = "https://checkout.razorpay.com/v1/checkout.js";
            script.onload  = ()=>resolve(true);
            script.onerror = ()=> resolve(false);
            document.body.appendChild(script);
        })
    }
    const deleteOrderOnFailure = async(orderId) =>
    {
        try
        {
            await deleteOrder(orderId);
        }
        catch(error)
        {
            console.log(error);
            toast.error("Something went wrong 👎")
        }
    }

    const completePayment = async (paymentMode) =>
    {
        if(!customerName || !mobileNumber)
        {
            toast.error("Please enter customer details");
            console.error(customerName,mobileNumber)
            return;
        }
        if(cartItems.length === 0)
        {
            toast.error("Your cart is empty");
            return;
        }
        const orderData = {
            customerName:customerName,
            phoneNumber:mobileNumber,
            cartItems,
            subTotal:totalAmount,
            tax,
            grandTotal,
            paymentMethod:paymentMode.toUpperCase()
        }
        console.log("Order Data:",orderData);
        setIsProcessing(true);
        try 
        {
           const response= await createOrder(orderData); 
           const savedOrderData = response.data;
           if(response.status === 201 && paymentMode === "cash")
           {
            toast.success("Cash received");
            setOrderDetails(savedOrderData);
           }
           else if(response.status === 201 && paymentMode === "upi")
           {
            const razorpayLoaded = await loadRazorpayScript();
            if(!razorpayLoaded)
            {
                toast.error("unable to load razorpay");
                await deleteOrderOnFailure(savedOrderData.orderId);
                return;
            }
            const razorpayResponse =await createRazorpayOrder({amount:grandTotal,currency:'INR'});
            const options ={
                key : AppConstants.RAZORPAY_KEY_ID,
                amount : razorpayResponse.data.amount,
                currency : razorpayResponse.data.currency,
                order_id : razorpayResponse.data.id,
                name:"My Retail Shop",
                description : "Order payment",
                handler : async function(response)
                {
                    await verifyPaymentHandler(response,savedOrderData);
                },
                prefill:
                {
                    name:customerName,
                    contact:mobileNumber
                },
                theme:
                {
                    color:'#3399cc'
                },
                modal:
                {
                    ondismiss:async() =>
                    {
                        await deleteOrderOnFailure(savedOrderData.orderId);
                        toast.error("Payment cancelled");
                    }
                },
            };
            const rzp =new window.Razorpay(options);
            rzp.on("payment.failed",async (response)=>
            {
                await deleteOrderOnFailure(savedOrderData.orderId);
                toast.error("Payment Failed");
                console.error(response.error.description);
            });
            rzp.open();
           } 
        } 
        catch (error) 
        {
            console.log(error);
            toast.error("Payment processing failed 147");
        }
        finally
        {
            setIsProcessing(false);
        }
    }

    const verifyPaymentHandler =  async (response,savedOrderData) =>
    {
        const paymentData = 
        {
            razorpayOrderId: response.razorpay_order_id,
            razorpayPaymentId: response.razorpay_payment_id,
            razorpaySignature: response.razorpay_signature,
            orderId: savedOrderData.orderId
        };
        try
        {
            const paymentResponse = await verifyPayment(paymentData);
            if(paymentResponse.status === 200)
            {
                toast.success("Payment sucessfull.");
                setOrderDetails({
                    ...savedOrderData,
                    paymentDetails:
                    {
                        razorpayOrderId:response.razorpay_order_id,
                        razorpayPaymentId: response.razorpay_payment_id,
                        razorpaySignature: response.razorpay_signature,
                    },
                });
            }
            else
            {
                toast.error("Payment Processing failed 182")
            }
        }
        catch(error)
        {
            console.error(error);
            toast.error("Payment Failed 188");
        }
    };
    return(
        <div className="mt-2">
            <div className="cart-summary-details">
                <div className="d-flex justify-content-between mb-2">
                    <span className="text-light">Item:</span>
                    <span className="text-light">₹{totalAmount.toFixed(2)}</span>
                </div>
                <div className="d-flex justify-content-between mb-2">
                    <span className="text-light">Tax(1%):</span>
                    <span className="text-light">₹{tax.toFixed(2)}</span>
                </div>
                <div className="d-flex justify-content-between mb-4">
                    <span className="text-light">Total:</span>
                    <span className="text-light">₹{grandTotal.toFixed(2)}</span>
                </div>
            </div>
            <div className="d-flex gap-3">
                <button className="btn btn-success flex-grow-1"
                onClick={()=>completePayment("cash")}
                disabled={isProcessing}>
                    {isProcessing? "Processing...":"Cash💵"}
                </button>
                <button className="btn btn-primary flex-grow-1"
                onClick={()=>completePayment("upi")}
                disabled={isProcessing}>
                    {isProcessing?"Processing Upi...":"UPI💳"}
                </button>
            </div>
            <div className="d-flex gap-3 mt-3">
                <button className="btn btn-warning flex-grow-1"
                onClick={placeOrder}
                disabled={isProcessing || !orderDetails}>
                    Place Order 🧾
                </button>
            </div>
            {
                showPopup && (
                    <ReceiptPopup
                    orderDetails={
                        {...orderDetails,
                            razorpayOrderId:orderDetails.paymentDetails?.razorpayOrderId,
                            razorpayPaymentId:orderDetails.paymentDetails.razorpayPaymentId,
                        }}
                        onClose={() => setShowPopup(false)}
                        onPrint={handlerPrintReceipt}
                             />
                )
            }
        </div>
    )
}
export default CartSummary;