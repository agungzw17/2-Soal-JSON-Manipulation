data class UserModel (
    var id:Int? = 0,
    var username:String? = "",
    var userProfile:ArrayList<UserProfileModel>? = ArrayList(),
    var userArticles:ArrayList<UserArticleModel>? = ArrayList()
)