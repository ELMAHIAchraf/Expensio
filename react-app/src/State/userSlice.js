import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { axiosInstance } from '../Axios';


export const fetchUser = createAsyncThunk("user/fetchUser", async () => {
    const response = await axiosInstance.get("/user")
    return response?.data
})
const userSlice = createSlice({
    name: 'user',
    initialState: {
        loading: false,
        error: null,
       id: null,
       fname : '',
       lname : '',
       currency : '',
       email : ''
    },
    reducers: {
        deleteUser: (state) => {
            state.loading = false;
            state.error = null;
            state.id = null;
            state.fname = '';
            state.lname = '';
            state.currency = '';
            state.email = '';
        }
    },
    extraReducers: (builder) => {
        builder
            .addCase(fetchUser.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(fetchUser.fulfilled, (state, action) => {
                state.id = action.payload.id;
                state.fname = action.payload.fname; 
                state.lname = action.payload.lname; 
                state.currency = action.payload.currency;
                state.email = action.payload.email;
                state.loading = false;
            })
            .addCase(fetchUser.rejected, (state, action) => {
                state.loading = false;
                state.error = action.error.message;
            });
    }
});


export const { deleteUser } = userSlice.actions;

export default userSlice.reducer;