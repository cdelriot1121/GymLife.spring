package com.gymcj.gimnasio.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    @Value("${security.jwt.llave.private}")
    private String privateKey;

    @Value("${security.jwt.usuario.generator}")
    private String userGenerator;

    /**
     * Metodo para la creacion de un token
     * @param authentication parametro para la obtención de los usuarios y sus permisos
     * @return jwtToken (codificado)
     */
    public String crearToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        String usuario = authentication.getPrincipal().toString();

        String permisos = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String jwtToken = JWT.create()
                .withIssuer(this.userGenerator) // usuario que generara el token
                .withSubject(usuario) // sujeto al que se le genera el token
                .withClaim("permisos", permisos) // generacion del claim con los permisos del usuario
                .withIssuedAt(new Date()) // fecha de creacion del token
                .withExpiresAt(new Date(System.currentTimeMillis() + 7200000)) // fecha de vigencia del token (hora de generacion actual en milisegundos + los milisegundos para la expiracion {2hrs})
                .withJWTId(UUID.randomUUID().toString()) // generacion del id del token
                .withNotBefore(new Date(System.currentTimeMillis())) // especifica el momento en el que el token se considera valido (en este caso, desde su generacion)
                .sign(algorithm); // firma del token

        return jwtToken;
    }

    /**
     * Método para decodificar y validar el token
     * @param token parámetro para la obtención del token de usuario
     * @return el token decodificado ó una excepción si el token es inválido
     */
    public DecodedJWT validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator) // usuario que genero el token (en este caso el backend)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);

            return decodedJWT;
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token Invalido, NO Autorizado");
        }
    }

    /**
     * Método para obtener el usuario que viene del token
     * @param decodedJWT parametro para la obtención del token decodificado
     * @return el nombre del usuario que genero el token
     */
    public String extraerUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject().toString();
    }

    /**
     * Método para obtener un Claim específico del token
     * @param decodedJWT parámetro para la obtención del token decodificado
     * @param nombreClaim parámetro con el nombre del Claim a obtener
     * @return el Claim solicitado
     */
    public Claim getClaimEspeficico(DecodedJWT decodedJWT, String nombreClaim) {
        return decodedJWT.getClaim(nombreClaim);
    }

    /**
     * Método para obtener todos los Claims
     * @param decodedJWT parámetro para la obtención del token decodificado
     * @return un mapa con todos los Claims del token
     */
    public Map<String, Claim> getClaims(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }
}
