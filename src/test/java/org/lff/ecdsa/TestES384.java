package org.lff.ecdsa;

import org.junit.Test;

public class TestES384 {

    @Test(expected = RuntimeException.class)
    public void invalidSetup() {
        JWTService service = JWTService.build();
        service.validate("");
    }

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

    @Test(expected = RuntimeException.class)
    public void testInvalid() {
        String privateKey = "ME4CAQAwEAYHKoZIzj0CAQYFK4EEACIENzA1AgEBBDB8eRSuhgqejpzOuu9GIBQT" +
                "nEtqVwMDIKUjkKRxrynnm/FO0m+DJMpMTpOJKhNMBpo=";
        String publicKey = "MHYwEAYHKoZIzj0CAQYFK4EEACIDYgAExxTHBRTm3YjP0j3v1VmPttoRVIlzmprZ" +
                "fTbK/ACi/Idmbn6Xwu7XyZjzFmQ7ezKiHSqkVJeaIMD22Hmj652SdEzR0znfnwnG" +
                "tyqqvABiicDNoaih+VGuMKg/tqIOl7h3";
        String jwt = "eyJhbGciOiJFUzM4NCIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.eBa0ZPnxlPqubBSf2gMHeM4D34ZEsPxBF6sPzvfpFkHwmesGR5KvFExc6WbqPtUp2puECC5LVbzgxFvh68IXZ5zR2rP0JSOwK1ZVekIPYQDn_VK5SyHSZnNjdXqoIFPf";

        JWTService service = JWTService.build().privateKey(privateKey).publicKey(publicKey);
        service.validate(jwt);

    }
}
