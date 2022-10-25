kalo pake preauthorize tambah @EnableGlobalMethodSecurity(prePostEnabled = true) di webSecurity nya
kalo pake session per user harus tambah sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)