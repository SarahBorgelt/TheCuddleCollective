import { BrowserRouter, Routes, Route } from 'react-router-dom';

import HomeView from './views/HomeView/HomeView';
import LoginView from './views/LoginView/LoginView';
import LogoutView from './views/LogoutView';
import RegisterView from './views/RegisterView/RegisterView';
import UserProfileView from './views/UserProfileView/UserProfileView';
import AvailablePets from './views/AvailablePetsView/AvailablePets';
import VolunteerApplicationForm from './views/VolunteerApplicationView/VolunteerApplicationView';
import VolunteerDirectory from './views/VolunteerDirectoryView/VolunteerDirectoryView';
import AddOrUpdatePets from './views/AddOrUpdatePetsView/AddOrUpdatePets';
import FirstLoginView from './views/FirstLoginView/FirstLoginView';
import AdminApplicationsView from './views/AdminApplicationView/AdminApplicationView';
import VolunteerActivationView from './views/VolunteerActivationView/VolunteerActivationView';
import MainNav from './components/MainNav/MainNav';
import ProtectedRoute from './components/ProtectedRoute';
import Footer from './components/Footer/Footer';
import Header from './components/Header/Header';
import AdoptedPets from './views/AdoptedPetsView/AdoptedPets';
import PetParentView from './views/PetParentView/PetParentView';



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
              <Route path="/register" element={<RegisterView />} />
              <Route path="/availablePets" element={<AvailablePets />} />
              <Route path='/adoptedPets' element={<AdoptedPets />} />
              <Route path="/first-login" element={<FirstLoginView />} />
              <Route path="/logout" element={<LogoutView />} />
              <Route path="/volunteer/apply" element={<VolunteerApplicationForm />} />

            <Route
              path="/userProfile"
              element={
                <ProtectedRoute>
                  <UserProfileView />
                </ProtectedRoute>
              }
            />

            <Route
              path="/activate-volunteer"
              element={
                <ProtectedRoute>
                  <VolunteerActivationView />
                </ProtectedRoute>
              }
            />

            <Route
              path="/volunteer/directory"
              element={
                <ProtectedRoute>
                  <VolunteerDirectory />
                </ProtectedRoute>
              }
            />

            <Route
              path="/petparents"
              element={
                <ProtectedRoute>
                  <PetParentView />
                </ProtectedRoute>
              }
            />

            <Route
              path="/addOrUpdatePets"
              element={
                <ProtectedRoute>
                  <AddOrUpdatePets />
                </ProtectedRoute>
              }
            />

            <Route
              path="/admin/applications"
              element={
                <ProtectedRoute>
                  <AdminApplicationsView />
                </ProtectedRoute>
              }
            />

            <Route
              path="/logout"
              element={
                <ProtectedRoute>
                  <LogoutView />
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
