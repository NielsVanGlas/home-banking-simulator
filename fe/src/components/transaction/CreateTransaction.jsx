import React, { useState } from 'react';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField, MenuItem, IconButton, Typography, Box } from '@mui/material';
import { Close } from '@mui/icons-material';
import axiosInstance from '../../axios';

const CreateTransaction = ({ open, onClose, onSuccess }) => {
    const [form, setForm] = useState({
        cause: '',
        value: '',
        type: 'DEPOSIT'
    });

    const handleSubmit = async () => {
        if (!form.cause || !form.value) return;

        try {
            await axiosInstance.post('/transaction', {
                cause: form.cause,
                value: form.type === 'DEPOSIT'
                    ? Number(form.value)
                    : -Math.abs(Number(form.value))
            });

            onSuccess?.();
            onClose();
            setForm({ cause: '', value: '', type: 'DEPOSIT' });
        } catch (err) {
            alert('Errore durante la creazione del movimento');
        }
    };

    return (
        <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
            <DialogTitle>
                <Typography variant="h6">Aggiungi Movimento</Typography>
                <IconButton onClick={onClose} sx={{ position: 'absolute', right: 8, top: 8 }}>
                    <Close />
                </IconButton>
            </DialogTitle>

            <DialogContent dividers>
                <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2, pt: 1 }}>
                    <TextField
                        label="Descrizione"
                        value={form.cause}
                        onChange={(e) => setForm({ ...form, cause: e.target.value })}
                        fullWidth
                    />
                    <TextField
                        label="Importo"
                        type="number"
                        value={form.value}
                        onChange={(e) => setForm({ ...form, value: e.target.value })}
                        InputProps={{ inputProps: { min: 0, step: '0.01' } }}
                        fullWidth
                    />
                    <TextField
                        select
                        label="Tipo"
                        value={form.type}
                        onChange={(e) => setForm({ ...form, type: e.target.value })}
                        fullWidth
                    >
                        <MenuItem value="DEPOSIT">Accredito (+)</MenuItem>
                        <MenuItem value="WITHDRAWAL">Addebito (-)</MenuItem>
                    </TextField>
                </Box>
            </DialogContent>

            <DialogActions>
                <Button onClick={onClose}>Annulla</Button>
                <Button
                    variant="contained"
                    onClick={handleSubmit}
                    disabled={!form.cause || !form.value}
                >
                    Aggiungi
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default CreateTransaction;