use std::ffi::c_char;

#[no_mangle]
extern "C" fn allocate_cstring(len: usize) -> *mut c_char {
    vec![0; len].leak().as_mut_ptr()
}

#[no_mangle]
extern "C" fn free_cstring(ptr: *mut c_char, len: usize) {
    unsafe { Vec::from_raw_parts(ptr, len, len) };
}
