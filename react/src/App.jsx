import { BrowserRouter, Routes, Route } from 'react-router-dom';
import HomeView from './views/HomeView/HomeView';
import LoginView from './views/LoginView/LoginView';
import LogoutView from './views/LogoutView';
import RegisterView from './views/RegisterView/RegisterView';
import UserProfileView from './views/UserProfileView/UserProfileView';
import MainNav from './components/MainNav/MainNav';
import ProtectedRoute from './components/ProtectedRoute';
import Footer from './components/Footer/Footer';
import Header from './components/Header/Header';

export default function App() {

  return (
    <BrowserRouter>
      <div id="app">
        <header id ="app-header">
          <Header/>
        </header>
          <nav id="main-nav">
            <MainNav />
          </nav>
          <main id="main-content">
            <Routes>
              <Route path="/" element={<HomeView />} />
              <Route path="/login" element={<LoginView />} />
              <Route path="/logout" element={<LogoutView />} />
              <Route path="/register" element={<RegisterView />} />
              <Route
                path="/userProfile"
                element={
                  <ProtectedRoute>
                    <UserProfileView />
                  </ProtectedRoute>
                }
              />
            </Routes>
          </main>
          <footer id = "app-footer">
            <Footer/>
          </footer>
      </div>
    </BrowserRouter>

  );
}
