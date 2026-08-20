import React, { useState } from 'react';
import axiosInstance from '../../axios';
import { Dialog, DialogContent, DialogActions, Box, Button, TextField, Typography, Alert, MenuItem, Select, InputLabel, FormControl, FormControlLabel, Checkbox, CircularProgress, Grid } from '@mui/material';

const UpdateAccount = ({ open, onClose, user }) => {

    console.log(user?.residence?.id);

    const [formData, setFormData] = useState({
        password: null,
        email: user?.email || null,
        mobile: user?.mobile || null,
        residence: {
            address: user?.residence?.address || null,
            city: user?.residence?.city || null,
            zipCode: user?.residence?.zipCode || null,
            provinceCode: user?.residence?.provinceCode || null,
            countryCode: user?.residence?.countryCode || null,
        },
        home: {
            address: user?.home?.address || null,
            city: user?.home?.city || null,
            zipCode: user?.home?.zipCode || null,
            provinceCode: user?.home?.provinceCode || null,
            countryCode: user?.home?.countryCode || null,
        },
        marketingConsensus: user?.marketingConsensus || false,
        documentType: user?.documentType || null,
        documentId: user?.documentId || null,
    });

    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const [sameAsResidence, setSameAsResidence] = useState(
        JSON.stringify(user?.residence?.id) == JSON.stringify(user?.home?.id)
    );

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleAddressChange = (type, field, value) => {
        setFormData({
            ...formData,
            [type]: { ...formData[type], [field]: value },
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setLoading(true);
        if (sameAsResidence) {
            formData.home = { ...formData.residence };
        }
        try {
            await axiosInstance.put('/user', formData);
            alert('Profilo aggiornato con successo!');
            window.location.reload();
        } catch (err) {
            setError(err.response?.data?.message || 'Errore durante il salvataggio');
        } finally {
            setLoading(false);
        }
    };

    return (
        <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
            <Box sx={{ textAlign: 'center', mb: 3 }}>
                <Typography variant="h4" fontWeight="bold">Modifica Profilo</Typography>
            </Box>
            {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
            <Box component="form" onSubmit={handleSubmit}>
                <DialogContent dividers>

                    <Typography variant="h6" gutterBottom sx={{ mt: 3 }}>Contatti</Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12} sm={8}><TextField fullWidth size="small" label="E-mail *" name="email" value={formData.email} onChange={handleChange} /></Grid>
                        <Grid item xs={12} sm={4}><TextField fullWidth size="small" label="Cellulare" name="mobile" value={formData.mobile} onChange={handleChange} /></Grid>
                    </Grid>

                    <Typography variant="h6" gutterBottom sx={{ mt: 3 }}>Residenza</Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12}><TextField fullWidth size="small" label="Indirizzo" value={formData.residence.address} onChange={(e) => handleAddressChange('residence', 'address', e.target.value)} /></Grid>
                        <Grid item xs={12} sm={6}><TextField fullWidth size="small" label="Città" value={formData.residence.city} onChange={(e) => handleAddressChange('residence', 'city', e.target.value)} /></Grid>
                        <Grid item xs={12} sm={3}><TextField fullWidth size="small" label="CAP" value={formData.residence.zipCode} onChange={(e) => handleAddressChange('residence', 'zipCode', e.target.value)} /></Grid>
                        <Grid item xs={12} sm={3}><TextField fullWidth size="small" label="Provincia" value={formData.residence.provinceCode} onChange={(e) => handleAddressChange('residence', 'provinceCode', e.target.value)} /></Grid>
                        <Grid item xs={12} sm={3}><TextField fullWidth size="small" label="Stato" value={formData.residence.countryCode} onChange={(e) => handleAddressChange('residence', 'countryCode', e.target.value)} /></Grid>
                    </Grid>
                    <FormControlLabel
                        control={<Checkbox checked={!sameAsResidence} onChange={(e) => setSameAsResidence(!e.target.checked)} color="primary" />}
                        label="Domicilio diverso dalla residenza"
                        sx={{ mt: 2, mb: 1 }}
                    />
                    {!sameAsResidence && (
                        <>
                            <Typography variant="h6" gutterBottom sx={{ mt: 3 }}>
                                Domicilio
                            </Typography>
                            <Grid container spacing={2}>
                                <Grid item xs={12}><TextField fullWidth size="small" label="Indirizzo" value={formData.home.address} onChange={(e) => handleAddressChange('home', 'address', e.target.value)} /></Grid>
                                <Grid item xs={12} sm={6}><TextField fullWidth size="small" label="Città" value={formData.home.city} onChange={(e) => handleAddressChange('home', 'city', e.target.value)} /></Grid>
                                <Grid item xs={12} sm={3}><TextField fullWidth size="small" label="CAP" value={formData.home.zipCode} onChange={(e) => handleAddressChange('home', 'zipCode', e.target.value)} /></Grid>
                                <Grid item xs={12} sm={3}><TextField fullWidth size="small" label="Provincia" value={formData.home.provinceCode} onChange={(e) => handleAddressChange('home', 'provinceCode', e.target.value)} /></Grid>
                                <Grid item xs={12} sm={3}><TextField fullWidth size="small" label="Stato" value={formData.home.countryCode} onChange={(e) => handleAddressChange('home', 'countryCode', e.target.value)} /></Grid>
                            </Grid>

                        </>
                    )}

                    <Typography variant="h6" gutterBottom sx={{ mt: 3 }}>Documento</Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12} sm={6}>
                            <FormControl fullWidth>
                                <InputLabel>Tipo documento</InputLabel>
                                <Select name="documentType" value={formData.documentType} onChange={handleChange} label="Tipo documento" size="small" >
                                    <MenuItem value="ID">Carta d'identità</MenuItem>
                                    <MenuItem value="PASSPORT">Passaporto</MenuItem>
                                </Select>
                            </FormControl>
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField fullWidth size="small" label="Numero documento" name="documentId" value={formData.documentId} onChange={handleChange} />
                        </Grid>
                    </Grid>

                    <TextField margin="normal" fullWidth type="password" label="Nuova password (lascia vuoto per non cambiare)" name="password" value={formData.password} onChange={handleChange} />

                    <FormControlLabel
                        control={<Checkbox checked={formData.marketingConsensus} onChange={e => setFormData(prev => ({ ...prev, marketingConsensus: e.target.checked }))} />}
                        label="Voglio ricevere offerte promozionali"
                        sx={{ mt: 2 }}
                    />
                </DialogContent>

                <DialogActions sx={{ p: 3 }}>
                    <Button onClick={onClose}>Annulla</Button>
                    <Button type="submit" variant="contained" disabled={loading}>
                        {loading ? <CircularProgress size={24} /> : 'Salva Modifiche'}
                    </Button>
                </DialogActions>
            </Box>
        </Dialog>
    );
};

export default UpdateAccount;