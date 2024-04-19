# Rknn_jextract_poc
  
compile:  
```  
javac *.java  
```  
  
send stuff on the device:  
```  
adb push someFilesNames  
```  

run on (on firefly device):  
1 - Send the library file(.so) onto the device, get it from [there](todo).  
2 - Setup the LD_LIBRARY_PATH environment variable to have the library file path in it.  
3 - Send the classes on the device.  
4 - Send a proper jdk22 on your device (I asked KAT for mine).  
5 - Execute with: `path/to/your/java22 --enable-native-access=ALL-UNNAMED -cp . org.rknn.Main`  

This was based on [this work](https://clehaxze.tw/gemlog/2023/09-02-benchmarking-rk3588-npu-matrix-multiplcation-performance-ep2.gmi) in c++.  

Comparison with initial work:  
roc_rk3588s_pc:/data/microdoc # ./jdk/bin/java --enable-native-access=ALL-UNNAMED -cp . org.rknn.Main 
Size = 256 time = 0.00126035807
Size = 512 time = 0.0038540170400000002
Size = 1024 time = 0.0115888624
Size = 2048 time = 0.06323490967999999
Size = 4096 time = 0.84754899555
Size = 8192 time = 14.975589004349999
roc_rk3588s_pc:/data/microdoc # ./rknn_matmul_api_demo 
size,rknn
256,0.00027
512,0.00088
1024,0.0044
2048,0.03026
4096,0.78822
8192,14.7231