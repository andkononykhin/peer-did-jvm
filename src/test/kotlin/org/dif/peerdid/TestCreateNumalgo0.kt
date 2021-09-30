package org.dif.peerdid

import org.dif.peerdid.model.EncodingType
import org.dif.peerdid.model.PublicKeyAuthentication
import org.dif.peerdid.model.PublicKeyTypeAuthentication
import org.dif.peerdid.createPeerDIDNumalgo0
import org.dif.peerdid.isPeerDID
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TestCreateNumalgo0 {
    @Test
    fun testCreateNumalgo0Positive() {
        val signingKeys = listOf(
            PublicKeyAuthentication(
                encodedValue = "ByHnpUCFb1vAfh9CFZ8ZkmUZguURW8nSw889hy6rD8L7",
                type = PublicKeyTypeAuthentication.ED25519,
                encodingType = EncodingType.BASE58,
            )
        )

        val peerDIDAlgo0 = createPeerDIDNumalgo0(signingKeys[0])
        assert(
            peerDIDAlgo0 == "did:peer:0z6MkqRYqQiSgvZQdnBytw86Qbs2ZWUkGv22od935YF4s8M7V"
        )
        assert(isPeerDID(peerDIDAlgo0))
    }

    @Test
    fun testCreateNumalgo0MalformedInceptionKeyNotBase58Encoded() {
        val signingKeys = listOf(
            PublicKeyAuthentication(
                encodedValue = "zx8xB2pv7cw8q1Pd0DacS",
                type = PublicKeyTypeAuthentication.ED25519,
                encodingType = EncodingType.BASE58,
            )
        )

        assertThrows<IllegalArgumentException> { createPeerDIDNumalgo0(signingKeys[0]) }
    }

    @Test
    fun testCreateNumalgo0MalformedShortInceptionKey() {
        val signingKeys = listOf(
            PublicKeyAuthentication(
                encodedValue = "ByHnp",
                type = PublicKeyTypeAuthentication.ED25519,
                encodingType = EncodingType.BASE58,
            )
        )

        assertThrows<IllegalArgumentException> { createPeerDIDNumalgo0(signingKeys[0]) }
    }

    @Test
    fun testCreateNumalgo0MalformedLongInceptionKey() {
        val signingKeys = listOf(
            PublicKeyAuthentication(
                encodedValue = "ByHnpUCFb1vAfh9CFZ8ByHnpUCFbZkmUZguURW8HnpUCFbZkmUnByHnpUCFbSHnpUCFbZkmUw889hByHnpUCFby6rD8L7",
                type = PublicKeyTypeAuthentication.ED25519,
                encodingType = EncodingType.BASE58,
            )
        )

        assertThrows<IllegalArgumentException> { createPeerDIDNumalgo0(signingKeys[0]) }
    }

    @Test
    fun testCreateNumalgo0MalformedEmptyInceptionKey() {
        val signingKeys = listOf(
            PublicKeyAuthentication(
                encodedValue = "",
                type = PublicKeyTypeAuthentication.ED25519,
                encodingType = EncodingType.BASE58,
            )
        )

        assertThrows<IllegalArgumentException> { createPeerDIDNumalgo0(signingKeys[0]) }
    }
}
