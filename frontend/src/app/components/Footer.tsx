import React from 'react'
import logo from "../../../assets/images/logo.png";

const Footer = () => {
  return (
    <footer className='footer'>
      <div className="text-stone-100 bg-gray-800 align-bottom p-4">
          <h2 className="text-center mb-4 text-2xl font-bold text-orange-500">SuperPrice Website</h2>
          <div className="flex">
              <div className="flex justify-center items-center w-1/2 ">
                <img src={logo.src} alt="SuperPrice" className="rounded bg-stone-100" style={{width: "300px", height: "auto"}}/>
              </div>
              <div className="bg-white w-px"></div>
              <div className="text-sm w-1/2 px-8">
                  <p>Contributors:</p>
                  <ul className="list-disc list-inside">
                    <li>Andy Than (s3947037)</li>
                    <li>Brian Tran (s3944192)</li>
                    <li>Daniel Azoor (s3944498)</li>
                    <li>Michael Xie (s3943224)</li>
                    <li>Tyler Xia (s3945694)</li>
                  </ul>
              </div>
          </div>
      </div>
    </footer>
  )
}

export default Footer