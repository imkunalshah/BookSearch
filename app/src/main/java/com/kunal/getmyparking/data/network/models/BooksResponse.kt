package com.kunal.getmyparking.data.network.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BooksResponse(
    @SerializedName("kind")
    var kind: String,
    @SerializedName("totalItems")
    var totalItems: Long,
    @SerializedName("items")
    var bookList: List<Book>
) : Serializable {
    data class Book(
        @SerializedName("kind")
        var kind: String,
        @SerializedName("id")
        var id: String,
        @SerializedName("etag")
        var etag: String,
        @SerializedName("selfLink")
        var selfLink: String,
        @SerializedName("volumeInfo")
        var volumeInfo: VolumeInfo,
        @SerializedName("saleInfo")
        var saleInfo: SaleInfo,
        @SerializedName("accessInfo")
        var accessInfo: AccessInfo
    ) : Serializable

    data class VolumeInfo(
        @SerializedName("title")
        var title: String,
        @SerializedName("subtitle")
        var subTitle: String,
        @SerializedName("authors")
        var authors: List<String>,
        @SerializedName("publishedDate")
        var publishedDate: String,
        @SerializedName("description")
        var description: String,
        @SerializedName("industryIdentifiers")
        var industryIdentifiers: List<IndustryIdentifiers>,
        @SerializedName("readingModes")
        var readingModes: ReadingModes,
        @SerializedName("pageCount")
        var pageCount: Int,
        @SerializedName("dimensions")
        var dimensions: Dimensions,
        @SerializedName("printType")
        var printType: String,
        @SerializedName("maturityRating")
        var maturityRating: String,
        @SerializedName("allowAnonLogging")
        var allowAnonLogging: Boolean,
        @SerializedName("contentVersion")
        var contentVersion: String,
        @SerializedName("panelizationSummary")
        var panelizationSummary: PanelizationSummary,
        @SerializedName("imageLinks")
        var imageLinks: ImageLinks,
        @SerializedName("language")
        var language: String,
        @SerializedName("previewLink")
        var previewLink: String,
        @SerializedName("infoLink")
        var infoLink: String,
        @SerializedName("canonicalVolumeLink")
        var canonicalVolumeLink: String,
    ) : Serializable

    data class IndustryIdentifiers(
        @SerializedName("type")
        var type: String,
        @SerializedName("identifier")
        var identifier: String,
    ) : Serializable

    data class ReadingModes(
        @SerializedName("text")
        var text: Boolean,
        @SerializedName("image")
        var image: Boolean,
    ) : Serializable

    data class PanelizationSummary(
        @SerializedName("containsEpubBubbles")
        var containsEpubBubbles: Boolean,
        @SerializedName("containsImageBubbles")
        var containsImageBubbles: Boolean
    ) : Serializable

    data class SaleInfo(
        @SerializedName("country")
        var country: String,
        @SerializedName("saleability")
        var saleability: String,
        @SerializedName("isEbook")
        var isEbook: Boolean,
        @SerializedName("listPrice")
        var listPrice: ListPrice,
        @SerializedName("retailPrice")
        var retailPrice: RetailPrice
    ) : Serializable

    data class AccessInfo(
        @SerializedName("country")
        var country: String,
        @SerializedName("viewability")
        var viewability: String,
        @SerializedName("embeddable")
        var embeddable: Boolean,
        @SerializedName("publicDomain")
        var publicDomain: Boolean,
        @SerializedName("textToSpeechPermission")
        var textToSpeechPermission: String,
        @SerializedName("epub")
        var epub: EPub,
        @SerializedName("pdf")
        var pdf: PDF,
        @SerializedName("webReaderLink")
        var webReaderLink: String,
        @SerializedName("accessViewStatus")
        var accessViewStatus: String,
        @SerializedName("quoteSharingAllowed")
        var quoteSharingAllowed: Boolean
    ) : Serializable

    data class EPub(
        @SerializedName("isAvailable")
        var isAvailable: Boolean
    ) : Serializable

    data class PDF(
        @SerializedName("isAvailable")
        var isAvailable: Boolean
    ) : Serializable

    data class ImageLinks(
        @SerializedName("smallThumbnail")
        var smallThumbnail: String,
        @SerializedName("thumbnail")
        var thumbnail: String
    ) : Serializable

    data class Dimensions(
        @SerializedName("height")
        var height: String,
        @SerializedName("width")
        var width: String,
        @SerializedName("thickness")
        var thickness: String
    ) : Serializable

    data class ListPrice(
        @SerializedName("amount")
        var amount: String,
        @SerializedName("currencyCode")
        var currencyCode: String
    ) : Serializable

    data class RetailPrice(
        @SerializedName("amount")
        var amount: String,
        @SerializedName("currencyCode")
        var currencyCode: String
    ) : Serializable
}
