import './CustomerForm.css'
const CustomerForm = ({customerName,setCustomerName,mobileNumber,setMobileNumber}) =>
{
    return (
        <div className="p-3">
            <div className="d-flex align-items-center gap-2">
                <label htmlFor="customerName" className='col-4 '>Customer Name💬</label>
                <input type="text"
                id="customerName"
                className="form-control form-control-sm"
                onChange={(e)=>setCustomerName(e.target.value)}
                value={customerName}
                required />
            </div>
            <div className="d-flex align-items-center gap-2 ">
                <label htmlFor="mobileNumber" className='col-4'>Customer Mobile📱</label>
                <input type="text"
                id="mobileNumber" 
                className="form-control form-control-sm"
                onChange={(e)=>setMobileNumber(e.target.value)}
                value={mobileNumber}
                required />
            </div>

        </div>
    )
}
export default CustomerForm;