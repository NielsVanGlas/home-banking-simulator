import React, { useContext } from 'react';
import { AuthenticationContext } from '../auth/AuthenticationContext';
import axiosInstance from '../../axios';
import { MenuItem, ListItemIcon } from '@mui/material';
import { DeleteForever } from '@mui/icons-material';

const DeleteAccount = () => {

    const { logout } = useContext(AuthenticationContext);

    const handleDeleteAccount = async () => {
        if (!window.confirm('Vuoi davvero eliminare il tuo account? Non si può annullare.')) return;
        try {
            await axiosInstance.delete('/user');
            logout();
        } catch (err) {
            alert('Impossibile eliminare l\'account');
        }
    };

    return (
        <MenuItem onClick={handleDeleteAccount} sx={{ color: 'error.main' }}>
            <ListItemIcon><DeleteForever fontSize="small" sx={{ color: 'error.main' }} /></ListItemIcon>
            Elimina Account
        </MenuItem>
    );

};

export default DeleteAccount;