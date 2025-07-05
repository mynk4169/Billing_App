import { useContext, useState } from 'react';
import './Explore.css'
import { AppContext } from '../../context/AppContext';
import DisplayItems from '../../components/DisplayItems/DisplayItems';
import CustomerForm from '../../components/CustomerForm/CustomerForm';
import CartSummary from '../../components/CartSummary/CartSummary';
import CartItems from '../../components/CartItems/CartItems';
import DisplayCategory from '../../components/DisplayCategory/DisplayCategory';
const Explore =()=>
{
    const {categories} = useContext(AppContext);
    const[selectedCategory,setSelectedCategory]=useState("");
    const[customerName,setCustomerName] = useState("");
    const[mobileNumber,setMobileNumber] = useState("");
    console.log(categories);
    return (
        <div className="explore-container text-light">
            <div className="left-column">
                <div className="first-row"style={{overflowY:'auto'}}>
                    <DisplayCategory
                     selectedCategory={selectedCategory}
                     setSelectedCategory={setSelectedCategory}
                    categories={categories}
                   />
                </div>
                <hr className="horizontal-line" />
                <div className="second-row"style={{overflowY:'auto'}}>
                    <DisplayItems selectedCategory={selectedCategory} />
                </div>
            </div>
            <div className="right-column d-flex flex-column">
                <div>
                    <div className="customer-form-container" style={{height:"10%"}}>
                        <CustomerForm
                            customerName={customerName}
                            setCustomerName={setCustomerName}
                            mobileNumber={mobileNumber}
                            setMobileNumber={setMobileNumber}
                        />
                    </div>
                </div>
                <hr className="my-3 text-light" />
                <div className="cart-items-container" style={{height:"60%",overflowY:'auto'}}>
                    <CartItems />

                </div>
                <div>
                    <div className="cart-summary-container" style={{height:"30%"}}>
                        <CartSummary
                            customerName={customerName}
                            mobileNumber={mobileNumber}
                            setCustomerName={setCustomerName}
                            setMobileNumber={setMobileNumber}
                        />
                    </div>
                </div>
            </div>
        </div>
    )
}
export default Explore;