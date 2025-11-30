import React, { useState, useEffect, forwardRef, useImperativeHandle } from 'react';
import axiosInstance from '../../axios';
import { Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TablePagination, CircularProgress, Alert, Chip, IconButton, Box } from '@mui/material';
import { Edit as EditIcon } from '@mui/icons-material';

const ShowCurrencies = forwardRef(({ onEdit }, ref) => {
    const [currencies, setCurrencies] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(15);
    const [totalItems, setTotalItems] = useState(0);

    const fetchCurrencies = async () => {
        setLoading(true);
        setError('');
        try {
            const res = await axiosInstance.get('/currency', {
                params: {
                    page: page + 1,
                    size: rowsPerPage,
                    sort: 'iso,asc'
                }
            });

            const data = res.data;

            setCurrencies(data.item || []);
            setTotalItems(data.totalItems || 0);

            if (page > 0 && data.item?.length === 0 && data.totalPages < page + 1) {
                setPage(0);
            }
        } catch (err) {
            setError('Impossibile caricare le valute');
            console.error(err);
            setCurrencies([]);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchCurrencies();
    }, [page, rowsPerPage]);

    useImperativeHandle(ref, () => ({
        refetch: fetchCurrencies
    }));

    const handlePageChange = (e, newPage) => setPage(newPage);
    const handleRowsPerPageChange = (e) => {
        setRowsPerPage(parseInt(e.target.value, 10));
        setPage(0);
    };

    if (loading) {
        return (
            <Paper elevation={3}>
                <Box sx={{ py: 8, textAlign: 'center' }}>
                    <CircularProgress />
                </Box>
            </Paper>
        );
    }

    if (error) {
        return (
            <Paper elevation={3}>
                <Alert severity="error" sx={{ m: 4 }}>{error}</Alert>
            </Paper>
        );
    }

    return (
        <Paper elevation={3}>
            <TableContainer>
                <Table stickyHeader>
                    <TableHead>
                        <TableRow>
                            <TableCell><strong>Codice ISO</strong></TableCell>
                            <TableCell><strong>Nome</strong></TableCell>
                            <TableCell><strong>Simbolo</strong></TableCell>
                            <TableCell><strong>Tasso vs EUR</strong></TableCell>
                            <TableCell align="center"><strong>Azioni</strong></TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {currencies.length === 0 ? (
                            <TableRow>
                                <TableCell colSpan={5} align="center" sx={{ py: 4 }}>
                                    Nessuna valuta trovata
                                </TableCell>
                            </TableRow>
                        ) : (
                            currencies.map((c) => (
                                <TableRow key={c.id} hover>
                                    <TableCell>
                                        <Chip label={c.iso} color="primary" size="small" />
                                    </TableCell>
                                    <TableCell>{c.name}</TableCell>
                                    <TableCell sx={{ fontWeight: 'bold', fontSize: '1.2em' }}>
                                        {c.symbol}
                                    </TableCell>
                                    <TableCell>
                                        {c.exchange != null ? Number(c.exchange).toFixed(6) : '-'}
                                    </TableCell>
                                    <TableCell align="center">
                                        <IconButton size="small" color="primary" onClick={() => onEdit(c)}>
                                            <EditIcon fontSize="small" />
                                        </IconButton>
                                    </TableCell>
                                </TableRow>
                            ))
                        )}
                    </TableBody>
                </Table>
            </TableContainer>

            <TablePagination
                component="div"
                count={totalItems}
                page={page}
                onPageChange={handlePageChange}
                rowsPerPage={rowsPerPage}
                onRowsPerPageChange={handleRowsPerPageChange}
                rowsPerPageOptions={[10, 15, 25, 50]}
                labelRowsPerPage="Righe per pagina:"
                labelDisplayedRows={({ from, to, count }) => `${from}–${to} di ${count}`}
            />
        </Paper>
    );
});

ShowCurrencies.displayName = 'ShowCurrencies';

export default ShowCurrencies;