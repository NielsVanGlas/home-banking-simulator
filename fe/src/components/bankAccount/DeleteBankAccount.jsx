import React, { useState } from 'react';
import { Button, Dialog, DialogTitle, DialogContent, DialogContentText, DialogActions, IconButton, CircularProgress } from '@mui/material';
import { DeleteForever, Close } from '@mui/icons-material';
import axiosInstance from '../../axios';

const DeleteBankAccount = ({ open, onClose, onDeleted }) => {
    const [deleting, setDeleting] = useState(false);

    const handleDelete = async () => {
        if (!window.confirm('Sei sicuro di voler eliminare il conto corrente? Questa azione è irreversibile.')) {
            return;
        }

        setDeleting(true);
        try {
            await axiosInstance.delete('/bank');
            onDeleted?.();
            onClose();
        } catch (err) {
            alert('Errore durante l\'eliminazione del conto');
        } finally {
            setDeleting(false);
        }
    };

    return (
        <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
            <DialogTitle>
                Elimina Conto Corrente
                <IconButton
                    onClick={onClose}
                    sx={{ position: 'absolute', right: 8, top: 8 }}
                    disabled={deleting}
                >
                    <Close />
                </IconButton>
            </DialogTitle>
            <DialogContent>
                <DialogContentText>
                    Stai per eliminare definitivamente il tuo conto corrente e tutti i movimenti associati.
                    <br /><br />
                    <strong>Questa azione non può essere annullata.</strong>
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose} disabled={deleting}>
                    Annulla
                </Button>
                <Button
                    variant="contained"
                    color="error"
                    startIcon={deleting ? <CircularProgress size={20} /> : <DeleteForever />}
                    onClick={handleDelete}
                    disabled={deleting}
                >
                    {deleting ? 'Eliminazione...' : 'Elimina Conto'}
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default DeleteBankAccount;