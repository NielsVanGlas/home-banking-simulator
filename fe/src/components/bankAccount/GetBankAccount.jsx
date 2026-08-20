import { useState, useEffect } from 'react';
import axiosInstance from '../../axios';

const GetBankAccount = () => {
  const [bankAccount, setBankAccount] = useState(null);
  const [loading, setLoading] = useState(true);

  const loadBankAccount = async () => {
    try {
      const res = await axiosInstance.get('/bank');
      setBankAccount(res.data);
    } catch (err) {
      if (err.response?.status === 404) {
        setBankAccount(null);
      } else {
        console.error('Errore caricamento conto:', err);
      }
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadBankAccount();
  }, []);

  return {
    bankAccount,
    loading,
    refresh: loadBankAccount,
    setBankAccount
  };
};

export default GetBankAccount;