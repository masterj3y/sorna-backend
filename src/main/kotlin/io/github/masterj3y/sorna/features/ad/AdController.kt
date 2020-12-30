package io.github.masterj3y.sorna.features.ad

import io.github.masterj3y.sorna.auth.config.AuthPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/ad")
class AdController
@Autowired
constructor(private val service: AdService) {

    @PostMapping
    fun postAd(
            @AuthenticationPrincipal
            authPrincipal: AuthPrincipal,
            categoryId: String,
            title: String,
            description: String,
            phoneNumber: String,
            price: Long,
            pic: Array<MultipartFile>): Ad {

        val userId = UUID.fromString(authPrincipal.userId)
        val ad = Ad(
                categoryId = UUID.fromString(categoryId),
                title = title,
                description = description,
                phoneNumber = phoneNumber,
                price = price
        )
        return service.save(
                userId = userId,
                ad = ad,
                picFiles = pic
        )
    }

    @GetMapping("{id}")
    fun getById(@AuthenticationPrincipal authPrincipal: AuthPrincipal, @PathVariable("id") adId: String): Optional<Ad> {
        return service.getById(UUID.fromString(adId))
    }

    @GetMapping
    fun getAds(@AuthenticationPrincipal authPrincipal: AuthPrincipal): MutableIterable<Ad> =
            service.getAds(UUID.fromString(authPrincipal.userId))

    @GetMapping("category/{id}")
    fun getAdsByCategory(@AuthenticationPrincipal authPrincipal: AuthPrincipal, @PathVariable("id") categoryId: String): MutableIterable<Ad> =
            service.getAdsByCategoryId(UUID.fromString(categoryId))

    @GetMapping("user-ads")
    fun getUserAds(@AuthenticationPrincipal authPrincipal: AuthPrincipal): MutableIterable<Ad> =
            service.getUserAds(UUID.fromString(authPrincipal.userId))

    @GetMapping("user-saved-ads")
    fun getUserSavedAds(@AuthenticationPrincipal authPrincipal: AuthPrincipal): MutableIterable<Ad> =
            service.getUserSavedAds(UUID.fromString(authPrincipal.userId))

    @GetMapping("/search/{keyword}")
    fun searchAds(@AuthenticationPrincipal authPrincipal: AuthPrincipal,
                  @PathVariable("keyword") keyword: String): MutableIterable<Ad> =
            service.searchAds(
                    userId = UUID.fromString(authPrincipal.userId),
                    keyword = keyword
            )

    @DeleteMapping("/{adId}")
    fun deleteAd(@AuthenticationPrincipal authPrincipal: AuthPrincipal, @PathVariable("adId") adId: String): Unit =
            service.deleteAd(
                    userId = UUID.fromString(authPrincipal.userId),
                    adId = UUID.fromString(adId)
            )

    @PutMapping("/save/{adId}")
    fun saveAd(@AuthenticationPrincipal authPrincipal: AuthPrincipal, @PathVariable("adId") adId: String) =
            service.savedAd(
                    userId = UUID.fromString(authPrincipal.userId),
                    adId = UUID.fromString(adId)
            )

    @PutMapping("/waste/{adId}")
    fun wastedAd(@AuthenticationPrincipal authPrincipal: AuthPrincipal, @PathVariable("adId") adId: String) =
            service.wastedAd(
                    userId = UUID.fromString(authPrincipal.userId),
                    adId = UUID.fromString(adId)
            )
}