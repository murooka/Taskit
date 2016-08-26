package me.murooka.taskit.service

import com.auth0.jwt.Algorithm
import com.auth0.jwt.JWTSigner
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.JWTVerifyException
import me.murooka.taskit.model.User
import java.math.BigInteger
import java.security.KeyFactory
import java.security.spec.RSAPrivateKeySpec
import java.security.spec.RSAPublicKeySpec

object SessionService {
    val issuer = "https://taskit.appspot.com"
    val modulus = BigInteger("129904290476933359701500629662765629831024332422809322403199992000089254364726027961195689658796504560023888379517240105868298923254332392406986739012875290155573020786033123311551550895479153293377501383312099228122457589533338931905441795166804727910520497291273965881769261787742914552493163870606950882467")
    val privateExponent = BigInteger("35276357746280467561951366496913803105158003023158483159280257833370286402017625457762785737180545213463312960469175002885974578286423754332165692573845936351326333081944150817280859058350430755952121772761131385531286542475703530610794546951122914897385714862217228841552077795269032284119069763374544074849")
    val publicExponent = BigInteger("65537")
    val keyFactory = KeyFactory.getInstance("RSA")
    val privateKeySpec = RSAPrivateKeySpec(modulus, privateExponent)
    val publicKeySpec = RSAPublicKeySpec(modulus, publicExponent)
    val privateKey = keyFactory.generatePrivate(privateKeySpec)
    val publicKey = keyFactory.generatePublic(publicKeySpec)

    val options = JWTSigner.Options().setAlgorithm(Algorithm.RS256)
    val signer = JWTSigner(privateKey)
    val verifier = JWTVerifier(publicKey)

    fun generate(user: User): String {
        try {
            val claim = mapOf(
                    "iss" to issuer,
                    "sub" to user.id.value
            )
            return signer.sign(claim, options)
        } catch (e: Exception) {
            e.printStackTrace()
            println(e.message)
            println(e.cause?.message)
            return ""
        }
    }

    fun verify(jws: String): String? {
        try {
            val claim = verifier.verify(jws)
            return claim["sub"] as? String
        } catch (e: JWTVerifyException) {
            return null
        }
    }
}
