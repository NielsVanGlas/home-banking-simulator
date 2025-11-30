import React, { useContext } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import { AuthenticationContext } from './components/auth/AuthenticationContext';
import Login from './pages/Login';
import Register from './pages/Register';
import Home from './pages/Home';
import AdminPanel from './pages/AdminPanel';

const App = () => {
    // Log the raw context FIRST to debug
    console.log('Raw AuthContext value:', useContext(AuthenticationContext));

    const contextValue = useContext(AuthenticationContext);
    if (contextValue === undefined) {
        console.error('AuthContext is undefined! Check if AuthProvider wraps <App /> in index.js');
        return <div>Context Error: AuthProvider missing</div>; // Fallback UI
    }

    const { user, isLoading } = contextValue;  // Safe destructure now

    if (isLoading) {
        return <div>Loading...</div>;
    }

    return (
        <Router>
            <Routes>
                <Route path="/login" element={user ? <Navigate to="/home" /> : <Login />} />
                <Route path="/register" element={user ? <Navigate to="/home" /> : <Register />} />
                <Route path="/home" element={user ? <Home /> : <Navigate to="/login" />} />
                <Route path="*" element={<Navigate to="/login" />} />
                <Route path="/admin" element={<AdminPanel />} />
            </Routes>
        </Router>
    );
};

export default App;
