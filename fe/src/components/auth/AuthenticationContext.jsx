import { createContext, useState, useEffect } from 'react';
import { publicAxios } from '../../axios';
import axiosInstance from '../../axios';

export const AuthenticationContext = createContext();

export const AuthenticationProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const refreshAccessToken = async () => {
      const refreshToken = localStorage.getItem('refreshToken');
      if (refreshToken) {
        try {
          const res = await publicAxios.post('/auth/refresh', { refreshToken });
          localStorage.setItem('accessToken', res.data.accessToken);
          // Fetch user details after refresh
          const userRes = await axiosInstance.get('/login');
          setUser(userRes.data);
        } catch (err) {
          localStorage.removeItem('accessToken');
          localStorage.removeItem('refreshToken');
          setUser(null);
        }
      }
      setIsLoading(false);
    };
    refreshAccessToken();
  }, []);

  const login = async (email, password) => {
    try {
      const res = await publicAxios.post('/auth/authenticate', { username: email, password });
      localStorage.setItem('accessToken', res.data.accessToken);
      localStorage.setItem('refreshToken', res.data.refreshToken);
      // Fetch full user details
      const userRes = await axiosInstance.get('/login');
      setUser(userRes.data);
      return true; // Success
    } catch (err) {
      console.error(err);
      return false; // Failure
    }
  };

  const logout = () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    setUser(null);
    window.location.href = '/login';
  };

  return (
    <AuthenticationContext.Provider value={{ user, isLoading, login, logout }}>
      {children}
    </AuthenticationContext.Provider>
  );
};