import React from "react";
import { Link, Outlet } from "react-router-dom";

const Layout = () => {
  const userEmail = localStorage.getItem("user-email");

  return (
    <>
      <aside className="sidebar-nav-wrapper">
        <div className="navbar-logo">
          <a href="index.html">NEXT-U Drive</a>
        </div>
        <nav className="sidebar-nav">
          <ul>
            <li className="nav-item">
              <Link to={`/dashboard`}>
                <span className="icon">
                  <svg
                    width="20"
                    height="20"
                    viewBox="0 0 20 20"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path d="M8.74999 18.3333C12.2376 18.3333 15.1364 15.8128 15.7244 12.4941C15.8448 11.8143 15.2737 11.25 14.5833 11.25H9.99999C9.30966 11.25 8.74999 10.6903 8.74999 10V5.41666C8.74999 4.7263 8.18563 4.15512 7.50586 4.27556C4.18711 4.86357 1.66666 7.76243 1.66666 11.25C1.66666 15.162 4.83797 18.3333 8.74999 18.3333Z" />
                    <path d="M17.0833 10C17.7737 10 18.3432 9.43708 18.2408 8.75433C17.7005 5.14918 14.8508 2.29947 11.2457 1.75912C10.5629 1.6568 10 2.2263 10 2.91665V9.16666C10 9.62691 10.3731 10 10.8333 10H17.0833Z" />
                  </svg>
                </span>
                <span className="text">Mon Drive</span>
              </Link>
            </li>
            <span className="divider">
              <hr />
            </span>
            <li className="nav-item">
              <Link to={`/dashboard/addBucket`}>
                <span className="icon">
                  <svg
                    width="20"
                    height="20"
                    viewBox="0 0 20 20"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path d="M8.74999 18.3333C12.2376 18.3333 15.1364 15.8128 15.7244 12.4941C15.8448 11.8143 15.2737 11.25 14.5833 11.25H9.99999C9.30966 11.25 8.74999 10.6903 8.74999 10V5.41666C8.74999 4.7263 8.18563 4.15512 7.50586 4.27556C4.18711 4.86357 1.66666 7.76243 1.66666 11.25C1.66666 15.162 4.83797 18.3333 8.74999 18.3333Z" />
                    <path d="M17.0833 10C17.7737 10 18.3432 9.43708 18.2408 8.75433C17.7005 5.14918 14.8508 2.29947 11.2457 1.75912C10.5629 1.6568 10 2.2263 10 2.91665V9.16666C10 9.62691 10.3731 10 10.8333 10H17.0833Z" />
                  </svg>
                </span>
                <span className="text">Nouveau Bucket</span>
              </Link>
            </li>
            <span className="divider">
              <hr />
            </span>
            <li className="nav-item">
              <Link to={`/dashboard/addFile`}>
                <span className="icon">
                  <svg
                    width="20"
                    height="20"
                    viewBox="0 0 20 20"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path d="M8.74999 18.3333C12.2376 18.3333 15.1364 15.8128 15.7244 12.4941C15.8448 11.8143 15.2737 11.25 14.5833 11.25H9.99999C9.30966 11.25 8.74999 10.6903 8.74999 10V5.41666C8.74999 4.7263 8.18563 4.15512 7.50586 4.27556C4.18711 4.86357 1.66666 7.76243 1.66666 11.25C1.66666 15.162 4.83797 18.3333 8.74999 18.3333Z" />
                    <path d="M17.0833 10C17.7737 10 18.3432 9.43708 18.2408 8.75433C17.7005 5.14918 14.8508 2.29947 11.2457 1.75912C10.5629 1.6568 10 2.2263 10 2.91665V9.16666C10 9.62691 10.3731 10 10.8333 10H17.0833Z" />
                  </svg>
                </span>
                <span className="text">Nouveau Fichier</span>
              </Link>
            </li>
            <span className="divider">
              <hr />
            </span>
          </ul>
        </nav>
      </aside>
      <main className="main-wrapper">
        <header className="header">
          <div className="container-fluid">
            <div className="row">
              <div className="col-lg-12 col-md-12 col-6">
                <div className="header-right">
                  <div className="profile-box ml-15">
                    <button
                      className="dropdown-toggle bg-transparent border-0"
                      type="button"
                      id="profile"
                      data-bs-toggle="dropdown"
                      aria-expanded="false"
                    >
                      <div className="profile-info">
                        <div className="info">
                          <div className="image">
                            <img
                              src="assets/images/profile/profile-image.png"
                              alt=""
                            />
                          </div>
                          <div>
                            <h6 className="fw-500">{userEmail}</h6>
                            <p>Admin</p>
                          </div>
                        </div>
                      </div>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </header>
        <section className="section">
          <div className="container-fluid">
            <Outlet />
          </div>
        </section>
        <footer className="footer">
          <div className="container-fluid">
            <div className="row">
              <div className="col-md-6 order-last order-md-first">
                <div className="copyright text-center text-md-start">
                  <p className="text-sm">Bucket-app</p>
                </div>
              </div>
              <div className="col-md-6">
                <div className="terms d-flex justify-content-center justify-content-md-end">
                  <a href="#0" className="text-sm">
                    Term & Conditions
                  </a>
                  <a href="#0" className="text-sm ml-15">
                    Privacy & Policy
                  </a>
                </div>
              </div>
            </div>
          </div>
        </footer>
      </main>
    </>
  );
};

export default Layout;
