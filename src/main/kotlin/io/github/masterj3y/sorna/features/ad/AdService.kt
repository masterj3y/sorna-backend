package io.github.masterj3y.sorna.features.ad

import io.github.masterj3y.sorna.features.ad.ad_picture.AdPicture
import io.github.masterj3y.sorna.features.ad.ad_picture.AdPictureRepository
import io.github.masterj3y.sorna.features.ad.saved_ad.SavedAd
import io.github.masterj3y.sorna.features.ad.saved_ad.SavedAdRepository
import io.github.masterj3y.sorna.filestorage.FileStorageService
import io.github.masterj3y.sorna.filestorage.FileStorageService.Companion.AD_PICTURE
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Service
class AdService
@Autowired
constructor(private val adRepository: AdRepository,
            private val adPicRepository: AdPictureRepository,
            private val savedAdRepository: SavedAdRepository,
            private val fileStorageService: FileStorageService) {

    fun save(userId: UUID, ad: Ad, picFiles: Array<MultipartFile>): Ad {

        val pics = mutableSetOf<AdPicture>()

        val createdAd = adRepository.save(ad.copy(userId = userId))

        picFiles.toList().stream().forEach {

            val fileName = generateFileName()
            val adPic = adPicture(fileName, createdAd)
            pics.add(adPic)

            adPicRepository.save(adPic)
            fileStorageService.save(fileName, it)
        }

        createdAd.pics = pics

        return createdAd
    }

    fun getById(adId: UUID): Optional<Ad> = adRepository.findById(adId)

    fun getAds(userId: UUID): MutableIterable<Ad> = adRepository.findAndLikes(userId)

    fun getAdsByCategoryId(categoryId: UUID): MutableIterable<Ad> = adRepository.findAllByCategoryId(categoryId)

    fun getUserAds(userId: UUID): MutableIterable<Ad> = adRepository.getUserAds(userId)

    fun getUserSavedAds(userId: UUID): MutableIterable<Ad> = adRepository.getUserSavedAds(userId)

    fun searchAds(userId: UUID, keyword: String) = adRepository.searchAds(userId, keyword)

    @Transactional
    fun deleteAd(userId: UUID, adId: UUID) {
        val pics = adPicRepository.findAllByAdId(adId)
        val picPaths = pics.map { Paths.get(it.picUrl) }
        fileStorageService.deleteFile(*picPaths.toTypedArray())

        adPicRepository.deleteByAdId(adId)
        adRepository.deleteAdById(userId, adId)
    }

    fun savedAd(userId: UUID, adId: UUID) {
        val savedAd = SavedAd(
                userId = userId,
                adId = adId
        )
        savedAdRepository.save(savedAd)
    }

    fun wastedAd(userId: UUID, adId: UUID) {
        savedAdRepository.deleteByUserIdAndAdId(userId, adId)
    }

    private fun adPicture(picUrl: Path, ad: Ad): AdPicture {
        return AdPicture(
                picUrl = picUrl.toString(),
                ad = ad
        )
    }

    private fun generateFileName(): Path {
        return FileStorageService.generateFileName(AD_PICTURE)
    }
}