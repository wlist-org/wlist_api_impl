use std::ffi::{c_char, CString};

#[no_mangle]
unsafe extern "C" fn allocate_cstring(len: usize) -> *mut c_char {
    CString::from_vec_with_nul_unchecked(vec![0; len]).into_raw()
}

#[repr(C)]
pub struct CTime {
    pub second: i64,
    pub nanos: i32,
    pub special: bool,
}
