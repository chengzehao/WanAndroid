#include <jni.h>
#include <string>
#include <string.h>

static char *EXTRA_SIGNATURE = (char *) "RICHSOFT";
static char *PACKAGE_NAME = (char *) "com.symbol.wanandroid";
static char *APP_SIGNATURE = (char *) "3082033d30820225a00302010202040d0c7445300d06092a864886f70d01010b0500304f310e300c060355040813056368696e61310b300906035504071302746a310b3009060355040a130270783111300f060355040b130872696368736f66743110300e060355040313077a686f756c696e301e170d3137303632333034303930385a170d3432303631373034303930385a304f310e300c060355040813056368696e61310b300906035504071302746a310b3009060355040a130270783111300f060355040b130872696368736f66743110300e060355040313077a686f756c696e30820122300d06092a864886f70d01010105000382010f003082010a0282010100c2002661ba78e5f7c669884be757ed4eca623318f1224fcf49767ad15dd9de15bb8828584b465edef5c27d09c0db6de45251298652cc77d55b547006d6bd752700360c7497b20966dd28ba67a524c8e2c2fea41f5d1d8dfed5bef9da1ae79e44d47b110b3dd91ba84ab403d36e55913bcf2d0897f63bdb2adde91012fc439c91b6b6f9b0b69d5924099bc8ff4a5a33ec0a5cdd67b37c5533376285a4727da661552926124537b629ce270036dcd70bdfd0d6bb66fce02e0369a76c4de22a3d40b1c91ce1e2f121bb1ee2ff80ea39abf77d111bfe57fd92bd4673dd1a69aca39f1eb36099698df0ab601a432929a0074405e1f81b8520c5273cf44307bee953750203010001a321301f301d0603551d0e0416041409f893a042f1c6604249acc134ad61c24fb07be5300d06092a864886f70d01010b050003820101003f8af2103d61f9b630b0685efe1d37e20910269795e7d46f2d24a45cbce6dc10ca98e1e0407bb0fc6e554c20c9037075256703bf618a28c68e858b5c33898adaf8b5a26a3c602f8d13b39f068b9e9bda4a572478b658e9d211c17ec1cb21c4da2277c0c880d01c09f953d0ac3d631ea1b2cf7c89ba72930499c4d1eec6ae205be2f693a9ca2eb2de9211b43f0e9f2b4d2380839da491305de29008a0ac85f1d80c7c8e22de082ea64a3d9c5403292dd4730df16630c34856a3257d6b766b4c920322270dee0b4a58b0b230ae6d966cbda7d853386ca163b4992e2548abbe5f30ca23bd02e24faf4392c3bfdf2f940de298d85aa4cc052fc67cbb010729073b39";
static char *SERVER = (char *) "http://www.wanandroid.com";
static int is_verify = 0;

using namespace std;

extern "C" {

JNIEXPORT void JNICALL
Java_com_sgitg_common_NdkMethods_signatureVerify(JNIEnv *env, jclass type,
                                                 jobject context);
JNIEXPORT jstring JNICALL
Java_com_sgitg_common_NdkMethods_getPreferencesPd(JNIEnv *env, jclass type);

JNIEXPORT jstring JNICALL
Java_com_sgitg_common_NdkMethods_getServerPath(JNIEnv *env, jclass type);
}


JNIEXPORT void JNICALL
Java_com_sgitg_common_NdkMethods_signatureVerify(JNIEnv *env, jclass type,
                                                 jobject context) {

    // 获取 PackageManager
    jclass j_clz = env->GetObjectClass(context);
    jmethodID j_mid = env->GetMethodID(j_clz, "getPackageName", "()Ljava/lang/String;");
    jstring j_package_name = (jstring) env->CallObjectMethod(context, j_mid);
    const char *c_pack_name = env->GetStringUTFChars(j_package_name, NULL);
    // 先比较包名是否相等，包名不想等返回
    if (strcmp(c_pack_name, PACKAGE_NAME) != 0) {
        jclass newExcCls = env->FindClass("java/lang/IllegalArgumentException");
        env->ThrowNew(newExcCls, "包名不符！");
        return;
    }

    j_mid = env->GetMethodID(j_clz, "getPackageManager", "()Landroid/content/pm/PackageManager;");
    jobject pack_manager = env->CallObjectMethod(context, j_mid);
    j_clz = env->GetObjectClass(pack_manager);
    j_mid = env->GetMethodID(j_clz, "getPackageInfo",
                             "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    jobject j_pack_info = env->CallObjectMethod(pack_manager, j_mid, j_package_name, 0x00000040);

    j_clz = env->GetObjectClass(j_pack_info);
    jfieldID j_fid = env->GetFieldID(j_clz, "signatures", "[Landroid/content/pm/Signature;");
    jobjectArray signatures_array = (jobjectArray) env->GetObjectField(j_pack_info, j_fid);
    jobject signature_obj = env->GetObjectArrayElement(signatures_array, 0);

    j_clz = env->GetObjectClass(signature_obj);
    j_mid = env->GetMethodID(j_clz, "toCharsString", "()Ljava/lang/String;");
    jstring j_signature = (jstring) env->CallObjectMethod(signature_obj, j_mid);
    const char *c_signature = env->GetStringUTFChars(j_signature, NULL);
    if (strcmp(APP_SIGNATURE, c_signature) == 0) {
        // 认证成功
        is_verify = 1;
    } else {
        // 认证失败
        is_verify = 0;
        jclass newExcCls = env->FindClass("java/lang/IllegalArgumentException");
        env->ThrowNew(newExcCls, "签名非法！");
    }
}

JNIEXPORT jstring JNICALL
Java_com_sgitg_common_NdkMethods_getPreferencesPd(JNIEnv *env, jclass type) {
    if (is_verify == 0) {
        return env->NewStringUTF("error signature");
    }
    return env->NewStringUTF(EXTRA_SIGNATURE);
}

JNIEXPORT jstring JNICALL
Java_com_sgitg_common_NdkMethods_getServerPath(JNIEnv *env, jclass type) {
    if (is_verify == 1) {
        return env->NewStringUTF(SERVER);
    } else {
        return env->NewStringUTF("");
    }
}