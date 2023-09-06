# jwt_ecdsa
A demo for validating ES384 jwt tokens by jdk (none 3rd party libraries).

When I need to validating a JWT token, I found that most of the 3rd party JWT libraries depend on https://www.bouncycastle.org/java.html.

However I found that in recent JDKs (9+) Oracle has implemented what is needed to validate a jwt token.

# This is what to do (ES384 for example)

## Create Private key and Public key from https://mkjwk.org/

Select `EC` and other configs as blow, then click `Generate`

![image](https://github.com/lff0305/jwt_ecdsa/assets/225183/4f6a2e5c-41b9-4bae-9f92-fa438d9d34ea)

You public key and private key is listed:

![image](https://github.com/lff0305/jwt_ecdsa/assets/225183/7a45f33f-5303-46cf-b3e8-0ec30bd8b30e)

## Copy the `Private Key` and `Public Key` to code, as

```
String privateKey = "ME4CAQAwEAYHKoZIzj0CAQYFK4EEACIENzA1AgEBBDB8eRSuhgqejpzOuu9GIBQT" +
                "nEtqVwMDIKUjkKRxrynnm/FO0m+DJMpMTpOJKhNMBpo=";
String publicKey = "MHYwEAYHKoZIzj0CAQYFK4EEACIDYgAExxTHBRTm3YjP0j3v1VmPttoRVIlzmprZ" +
                "fTbK/ACi/Idmbn6Xwu7XyZjzFmQ7ezKiHSqkVJeaIMD22Hmj652SdEzR0znfnwnG" +
                "tyqqvABiicDNoaih+VGuMKg/tqIOl7h3";
```
*The -----BEGIN/END PRIVATE KEY----- and \\n was removed.*

## Generate a JWT from jwt.io

Copy the `Private Key` and `Public Key` to https://jwt.io, and select `ES384`:

![image](https://github.com/lff0305/jwt_ecdsa/assets/225183/f0e4ce92-3823-4d0f-825d-5dda14cd7ade)

jwt.io will automatically generate a valid jwt token:

![image](https://github.com/lff0305/jwt_ecdsa/assets/225183/bfb52ddb-b472-40c0-9cbe-4719bbc485ab)

Copy this token to code (`TestES384.java`):
```
    @Test
    public void testValid() {
        String privateKey = "ME4CAQAwEAYHKoZIzj0CAQYFK4EEACIENzA1AgEBBDB8eRSuhgqejpzOuu9GIBQT" +
                "nEtqVwMDIKUjkKRxrynnm/FO0m+DJMpMTpOJKhNMBpo=";
        String publicKey = "MHYwEAYHKoZIzj0CAQYFK4EEACIDYgAExxTHBRTm3YjP0j3v1VmPttoRVIlzmprZ" +
                "fTbK/ACi/Idmbn6Xwu7XyZjzFmQ7ezKiHSqkVJeaIMD22Hmj652SdEzR0znfnwnG" +
                "tyqqvABiicDNoaih+VGuMKg/tqIOl7h3";
        String jwt = "eyJhbGciOiJFUzM4NCIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.eBa0ZPnxlPqubBSf2gMHeM4D34ZEsPxBF6sPzvfpFkHwmesGR5KvFExc6WbqPtUp2puECC5LVbzgxFvh68IXZ5zR2rP0JSOwK1ZVekIPYQDn_VK5SyHSZnNjdXqoIFPe";

        JWTService service = JWTService.build().privateKey(privateKey).publicKey(publicKey);
        service.validate(jwt);
    }
```
The unit tests should pass, which means the JWT signatures are valid.

**This demo code ONLY validates the signatures are valid; it will NOT check `exp` or other properties in the header so do NOT use it in production!!**

