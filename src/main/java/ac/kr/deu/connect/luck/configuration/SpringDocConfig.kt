package ac.kr.deu.connect.luck.configuration

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityScheme
import java.time.Instant
import org.springframework.boot.info.GitProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringDocConfig(
    private val gitProperties: GitProperties,
) {
    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI().components(
            Components().apply {
                securitySchemes(
                    mapOf(
                        BEARER_AUTH to securityScheme(),
                    ),
                )
            },
        ).info(info())
    }

    /**
     * Bearer Auth Security Scheme
     */
    private fun securityScheme(): SecurityScheme {
        return SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .description("Auth API를 통해 발급받은 Bearer Token을 입력해주세요")
            .scheme("bearer")
            .bearerFormat("JWT")
    }

    /**
     * OpenAPI Info
     */
    private fun info(): Info {
        return Info().apply {
            title = "Connect Luck을 위한 OpenAPI 문서"
            description =
                """<h1>필독 사항</h1>
                |<ul>
                |    <li>단순 텍스트 전송의 경우 application/json 으로 전송해야 합니다.</li>
                |    <li>사진이 포함된 경우 multipart/form-data 로 전송해야 합니다.</li>
                |    <li>Swagger에서 테스트를 진행할 때 이미지가 없다면 <b>Send Empty Value를 체크 해제</b> 해야 합니다.</li>
                |</ul>
                |<p>API Document is generated by Swagger</p>
                |<h4>Last Commit Information</h4>
                |<p><strong>Message:</strong> ${gitProperties.get("commit.message.full")}</p>
                |<p><strong>Commit ID:</strong> ${gitProperties.get("commit.id")}</p>
                |<p><strong>Commit Time:</strong> ${Instant.ofEpochMilli(gitProperties.get("commit.time").toLong())}</p>
                |<p><strong>Committed by:</strong> ${gitProperties.get("commit.user.name")} (${gitProperties.get("commit.user.email")})</p>
                """.trimMargin()
            termsOfService = ""
            contact =
                Contact().apply {
                    name = "Namju Kim"
                    email = "cmsong111@gmail.com"
                }
            license =
                License().apply {
                    name = "MIT License"
                    url = "https://opensource.org/licenses/MIT"
                }
            version = gitProperties.get("build.version")
        }
    }


    companion object {
        const val BEARER_AUTH = "Bearer Auth"
    }
}
