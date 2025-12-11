import { BrowserRouter, Routes, Route } from 'react-router-dom';
import HomeView from './views/HomeView/HomeView';
import LoginView from './views/LoginView/LoginView';
import LogoutView from './views/LogoutView';
import RegisterView from './views/RegisterView/RegisterView';
import UserProfileView from './views/UserProfileView/UserProfileView';
import AvailablePets from './views/AvailablePetsView/AvailablePets';
import MainNav from './components/MainNav/MainNav';
import ProtectedRoute from './components/ProtectedRoute';
import Footer from './components/Footer/Footer';
import Header from './components/Header/Header';
import VolunteerApplicationForm from './views/VolunteerApplicationView/VolunteerApplicationView';
import VolunteerDirectory from './views/VolunteerDirectoryView/VolunteerDirectoryView';
import AddOrUpdatePets from './views/AddOrUpdatePetsView/AddOrUpdatePets';

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
              <Route path="/availablePets" element={<AvailablePets />} />
              <Route path="/logout" element={<LogoutView />} />
              <Route path="/register" element={<RegisterView />} />
              <Route
                path="/addOrUpdatePets"
                element={
                  <ProtectedRoute>
                    <AddOrUpdatePets />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/userProfile"
                element={
                  <ProtectedRoute>
                    <UserProfileView />
                  </ProtectedRoute>
                }
              />
            <Route path="/volunteer/apply" element={<VolunteerApplicationForm />} />
        <Route path="/volunteer/directory" element={<VolunteerDirectory />}        />
            </Routes>
          </main>
          <footer id = "app-footer">
            <Footer/>
          </footer>
      </div>
    </BrowserRouter>

  );
}
