package ac.kr.deu.connect.luck.foodtruck.controller.request

import ac.kr.deu.connect.luck.foodtruck.entity.FoodType
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.multipart.MultipartFile

@Schema(description = "푸드트럭 수정 요청 DTO")
data class FoodTruckUpdateForm(
    @field:Schema(description = "상호명", example = "맛있는 푸드트럭")
    val name: String? = null,
    @field:Schema(description = "설명", example = "맛있는 음식을 판매하는 푸드트럭입니다.")
    val description: String? = null,
    @field:Schema(description = "썸네일", example = "thumbnail.jpg")
    val thumbnail: MultipartFile? = null,
    @field:Schema(description = "이미지들")
    val images: List<MultipartFile>? = null,
    @field:Schema(description = "음식 종류", example = "KOREAN")
    val foodType: FoodType? = null,
)
