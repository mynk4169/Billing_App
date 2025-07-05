import { useNavigate } from 'react-router-dom';
import './NotFound.css'

const NotFound = () =>
{
    const navigate =useNavigate();
    return(
        <div className="not-found-container">
            <div className="not-found-content">
                <div className="not-found-title">404🆘</div>
                <h2 className="not-found-subtitle">Oops😱! Page not Found😭</h2>
                <p className="not-found-messsage">
                    The page you're looking for doesn't exist or moved!
                </p>
                <button className="not-found-button" onClick={()=>navigate("/")}>
                    Go to Homepage🏠
                </button>
            </div>
        </div>
    )
}
export default NotFound;