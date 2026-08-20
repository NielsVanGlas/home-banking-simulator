import axios from 'axios';

// Public instance for login/refresh
export const publicAxios = axios.create({
	baseURL: process.env.REACT_APP_BASE_URL,
	headers: { 'Content-Type': 'application/json' },
});

// Authenticated instance with interceptors
const axiosInstance = axios.create({
	baseURL: process.env.REACT_APP_BASE_URL,
	headers: { 'Content-Type': 'application/json' },
});

// Request interceptor: Add access token to headers
axiosInstance.interceptors.request.use(
	(config) => {
		const token = localStorage.getItem('accessToken');
		if (token) {
			config.headers.Authorization = `Bearer ${token}`;
		}
		return config;
	},
	(error) => Promise.reject(error)
);

// Response interceptor: Handle 401 by refreshing token
axiosInstance.interceptors.response.use(
	(response) => response,
	async (error) => {
		const originalRequest = error.config;
		if (error.response?.status === 401 && !originalRequest._retry && originalRequest.url !== '/auth/authenticate') {
			originalRequest._retry = true;
			const refreshToken = localStorage.getItem('refreshToken');
			if (refreshToken) {
				try {
					const response = await publicAxios.post('/auth/refresh', { refreshToken });
					const { accessToken } = response.data;
					localStorage.setItem('accessToken', accessToken);
					originalRequest.headers.Authorization = `Bearer ${accessToken}`;
					return axiosInstance(originalRequest);
				} catch (refreshError) {
					// Refresh failed: Logout
					localStorage.removeItem('accessToken');
					localStorage.removeItem('refreshToken');
					window.location.href = '/login';
					return Promise.reject(refreshError);
				}
			}
		}
		return Promise.reject(error);
	}
);

export default axiosInstance;