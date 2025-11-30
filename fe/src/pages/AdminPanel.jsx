import React, { useContext, useState, useRef } from 'react';
import { AuthenticationContext } from '../components/auth/AuthenticationContext';
import { useNavigate } from 'react-router-dom';
import { Container, Box, Typography, AppBar, Toolbar, IconButton, Avatar, Menu, MenuItem, Divider, ListItemIcon, Button, Chip, CircularProgress } from '@mui/material';
import { AccountBalance, Logout, Add } from '@mui/icons-material';

import CreateCurrency from '../components/currency/CreateCurrency';
import UpdateCurrency from '../components/currency/UpdateCurrency';
import ShowCurrencies from '../components/currency/ShowCurrencies';
import ShowErrors from '../components/error/ShowErrors';

const AdminPanel = () => {
    const { user, isLoading: authLoading, logout } = useContext(AuthenticationContext);
    const navigate = useNavigate();

    const [anchorEl, setAnchorEl] = useState(null);
    const [createOpen, setCreateOpen] = useState(false);
    const [editCurrency, setEditCurrency] = useState(null);

    const showCurrenciesRef = useRef();

    const handleMenuOpen = (e) => setAnchorEl(e.currentTarget);
    const handleMenuClose = () => setAnchorEl(null);

    React.useEffect(() => {
        if (!authLoading && (!user || user.role !== 'ADMIN')) {
            navigate('/');
        }
    }, [user, authLoading, navigate]);

    if (authLoading || !user) {
        return (
            <Box sx={{ display: 'flex', justifyContent: 'center', mt: 10 }}>
                <CircularProgress />
            </Box>
        );
    }

    const initials = `${user.firstName?.[0] || ''}${user.lastName?.[0] || ''}`;

    return (
        <>
            <AppBar position="static" color="transparent" elevation={0} sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <Toolbar>
                    <AccountBalance sx={{ mr: 2, fontSize: 30 }} />
                    <Typography variant="h6" sx={{ flexGrow: 1 }}>
                        Admin - Pannello di Controllo
                    </Typography>
                    <Chip label="ADMIN" color="error" size="small" sx={{ mr: 2 }} />
                    <Typography variant="subtitle1" sx={{ mr: 2 }}>
                        {user.firstName} {user.lastName}
                    </Typography>
                    <IconButton onClick={handleMenuOpen}>
                        <Avatar sx={{ bgcolor: 'error.main' }}>{initials}</Avatar>
                    </IconButton>
                    <Menu anchorEl={anchorEl} open={Boolean(anchorEl)} onClose={handleMenuClose}>
                        <MenuItem onClick={() => { handleMenuClose(); navigate('/'); }}>
                            <ListItemIcon><AccountBalance fontSize="small" /></ListItemIcon>
                            Torna alla Home
                        </MenuItem>
                        <Divider />
                        <MenuItem onClick={logout}>
                            <ListItemIcon><Logout fontSize="small" /></ListItemIcon>
                            Esci
                        </MenuItem>
                    </Menu>
                </Toolbar>
            </AppBar>

            <Container maxWidth="lg" sx={{ mt: 6 }}>
                <Box display="flex" justifyContent="space-between" alignItems="center" mb={4}>
                    <Box>
                        <Typography variant="h4">Gestione Valute</Typography>
                        <Typography color="text.secondary">
                            Crea, modifica e visualizza tutte le valute del sistema
                        </Typography>
                    </Box>
                    <Button variant="contained" startIcon={<Add />} onClick={() => setCreateOpen(true)}>
                        Nuova Valuta
                    </Button>
                </Box>

                <ShowCurrencies ref={showCurrenciesRef} onEdit={setEditCurrency} />
            </Container>

            <Container maxWidth="lg" sx={{ mt: 10, mb: 8 }}>
                <ShowErrors />
            </Container>

            <CreateCurrency
                open={createOpen}
                onClose={() => setCreateOpen(false)}
                onSuccess={() => {
                    setCreateOpen(false);
                    showCurrenciesRef.current?.refetch?.();
                }}
            />

            <UpdateCurrency
                open={!!editCurrency}
                onClose={() => setEditCurrency(null)}
                currency={editCurrency}
                onSuccess={() => {
                    setEditCurrency(null);
                    showCurrenciesRef.current?.refetch?.();
                }}
            />
        </>
    );
};

export default AdminPanel;