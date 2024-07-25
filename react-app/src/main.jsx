import ReactDOM from 'react-dom/client'
import  App  from './Router/App'
import './index.css'
import { Toaster } from 'react-hot-toast'
import {store} from './State/store';
import { Provider } from 'react-redux';


ReactDOM.createRoot(document.getElementById('root')).render(
  <>
    <Provider store={store}>
        <App />
    </Provider>
    <Toaster
      position="bottom-right"
      reverseOrder={false}
      toastOptions={{
        style: {
          background: '#363636',
          color: '#fff',
        },
      }}
    />
  </>
)