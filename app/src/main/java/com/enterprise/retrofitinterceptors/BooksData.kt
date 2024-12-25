package com.enterprise.retrofitinterceptors

import com.google.gson.annotations.SerializedName


data class BooksData (

    @SerializedName("status"        ) var status       : String?  = null,
    @SerializedName("copyright"     ) var copyright    : String?  = null,
    @SerializedName("num_results"   ) var numResults   : Int?     = null,
    @SerializedName("last_modified" ) var lastModified : String?  = null,
    @SerializedName("results"       ) var results      : Results? = Results()

)

data class Isbn (

    @SerializedName("isbn10" ) var isbn10 : String? = null,
    @SerializedName("isbn13" ) var isbn13 : String? = null

)

data class BuyLink (

    @SerializedName("name" ) var name : String? = null,
    @SerializedName("url"  ) var url  : String? = null

)

data class Book (

    @SerializedName("rank"                 ) var rank               : Int?                = null,
    @SerializedName("rank_last_week"       ) var rankLastWeek       : Int?                = null,
    @SerializedName("weeks_on_list"        ) var weeksOnList        : Int?                = null,
    @SerializedName("asterisk"             ) var asterisk           : Int?                = null,
    @SerializedName("dagger"               ) var dagger             : Int?                = null,
    @SerializedName("primary_isbn10"       ) var primaryIsbn10      : String?             = null,
    @SerializedName("primary_isbn13"       ) var primaryIsbn13      : String?             = null,
    @SerializedName("publisher"            ) var publisher          : String?             = null,
    @SerializedName("description"          ) var description        : String?             = null,
    @SerializedName("price"                ) var price              : String?             = null,
    @SerializedName("title"                ) var title              : String?             = null,
    @SerializedName("author"               ) var author             : String?             = null,
    @SerializedName("contributor"          ) var contributor        : String?             = null,
    @SerializedName("contributor_note"     ) var contributorNote    : String?             = null,
    @SerializedName("book_image"           ) var bookImage          : String?             = null,
    @SerializedName("book_image_width"     ) var bookImageWidth     : Int?                = null,
    @SerializedName("book_image_height"    ) var bookImageHeight    : Int?                = null,
    @SerializedName("amazon_product_url"   ) var amazonProductUrl   : String?             = null,
    @SerializedName("age_group"            ) var ageGroup           : String?             = null,
    @SerializedName("book_review_link"     ) var bookReviewLink     : String?             = null,
    @SerializedName("first_chapter_link"   ) var firstChapterLink   : String?             = null,
    @SerializedName("sunday_review_link"   ) var sundayReviewLink   : String?             = null,
    @SerializedName("article_chapter_link" ) var articleChapterLink : String?             = null,
    @SerializedName("isbns"                ) var isbns              : ArrayList<Isbn>    = arrayListOf(),
    @SerializedName("buy_links"            ) var buyLinks           : ArrayList<BuyLink> = arrayListOf(),
    @SerializedName("book_uri"             ) var bookUri            : String?             = null

)


data class Results (

    @SerializedName("list_name"                  ) var listName                 : String?           = null,
    @SerializedName("list_name_encoded"          ) var listNameEncoded          : String?           = null,
    @SerializedName("bestsellers_date"           ) var bestsellersDate          : String?           = null,
    @SerializedName("published_date"             ) var publishedDate            : String?           = null,
    @SerializedName("published_date_description" ) var publishedDateDescription : String?           = null,
    @SerializedName("next_published_date"        ) var nextPublishedDate        : String?           = null,
    @SerializedName("previous_published_date"    ) var previousPublishedDate    : String?           = null,
    @SerializedName("display_name"               ) var displayName              : String?           = null,
    @SerializedName("normal_list_ends_at"        ) var normalListEndsAt         : Int?              = null,
    @SerializedName("updated"                    ) var updated                  : String?           = null,
    @SerializedName("books"                      ) var books                    : ArrayList<Book>  = arrayListOf(),
    @SerializedName("corrections"                ) var corrections              : ArrayList<String> = arrayListOf()

)