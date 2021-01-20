import org.json.JSONArray
import org.apache.commons.lang3.StringUtils
import java.util.HashSet




fun main() {
    val classloader = Thread.currentThread().contextClassLoader
    val inputStream = classloader.getResourceAsStream("profile_list.json")

    val jsonFile = inputStream.bufferedReader().use { it.readText() }

    val list = JSONArray(jsonFile)
    val listItemsUser = ArrayList<UserModel>()
    for (i in 0 until list.length()) {
        val user = list.getJSONObject(i)
        val userItem = UserModel(
            user.getInt("id"),
            user.getString("username")
        )

        for (i in 0 until user.getJSONObject("profile").length()) {
            val profileItem = UserProfileModel(
                user.getJSONObject("profile").getString("full_name"),
                user.getJSONObject("profile").getString("birthday"),
            )
            for (i in 0 until user.getJSONObject("profile").getJSONArray("phones").length()) {
                val phoneItem = UserPhoneModel(
                    user.getJSONObject("profile").getJSONArray("phones").toString()
                )
                profileItem.phone?.add(phoneItem)
            }
            userItem.userProfile?.add(profileItem)
        }

        for (i in 0 until user.getJSONArray("articles:").length()) {
            val article = user.getJSONArray("articles:").getJSONObject(i)
            val articleItem = UserArticleModel(
                article.getInt("id"),
                article.getString("title"),
                article.getString("published_at")
            )
            userItem.userArticles?.add(articleItem)
        }
        listItemsUser.add(userItem)
    }


    //1. Find users who doesn't have any phone numbers.
    println()
    println("Find users who doesn't have any phone numbers")
    val listUserDoesNotNumber = ArrayList<String>()
    for (listUser in listItemsUser) {
        for (listPhone in listUser.userProfile!!) {
            if (listPhone.phone!!.isEmpty()) {
                listUser.username?.let { listUserDoesNotNumber.add(it) }
            }
        }
    }
    for (items in listUserDoesNotNumber.distinct()) {
        println(items)
    }

    //2.Find users who have articles.
    println()
    println("Find users who have articles")
    val listUserHaveArticle = ArrayList<String>()
    for (i in 0 until listItemsUser.size) {
        if (listItemsUser[i].userArticles?.isEmpty() == false) {
            listItemsUser[i].username?.let { listUserHaveArticle.add(it) }
        }
    }
    for (items in listUserHaveArticle.distinct()) {
        println(items)
    }

    //3. Find users who have "annis" on their name.
    println()
    println("Find users who have \"annis\" on their name.")
    val listUserHaveAnnis = ArrayList<String>()
    for (listUser in listItemsUser) {
        for (listProfile in listUser.userProfile!!) {
            if (listProfile.full_name!!.toLowerCase().contains("annis", ignoreCase = true)) {
                listUser.username?.let { listUserHaveAnnis.add(it) }
            }
        }
    }
    for (items in listUserHaveAnnis.distinct()) {
        println(items)
    }

    //4. Find users who have articles on year 2020
    println()
    println("Find users who have articles on year 2020")
    val listUserHaveArticle2020 = ArrayList<String>()
    for (listUser in listItemsUser) {
        for (listArticle in listUser.userArticles!!) {
            if (listArticle.published_at!!.toLowerCase().contains("2020", ignoreCase = true)) {
                listUser.username?.let { listUserHaveArticle2020.add(it) }
            }
        }
    }
    for (items in listUserHaveArticle2020.distinct()) {
        println(items)
    }

    //5. Find users who are born on 1986.
    println()
    println("Find users who are born on 1986.")
    val listUserBorn1986 = ArrayList<String>()
    for (listUser in listItemsUser) {
        for (listProfile in listUser.userProfile!!) {
            if(listProfile.birthday!!.toLowerCase().contains("1986", ignoreCase = true) ) {
                listUser.username?.let { listUserBorn1986.add(it) }
            }
        }
    }
    for (items in listUserBorn1986.distinct()) {
        println(items)
    }

    //6. Find articles that contain "tips" on the title
    println()
    println("Find articles that contain \"tips\" on the title")
    val listTitleIsTips = ArrayList<String>()
    for (listUser in listItemsUser) {
        for (listArticle in listUser.userArticles!!) {
            if(listArticle.title!!.toLowerCase().contains("tips", ignoreCase = true) ) {
                listArticle.title?.let { listTitleIsTips.add(it) }
            }
        }
    }
    for (items in listTitleIsTips.distinct()) {
        println(items)
    }

    //7. Find articles published before August 2019
    println()
    println("Find articles published before August 2019")
    val listArticlePublishedBeforeAugust = ArrayList<String>()
    for (listUser in listItemsUser) {
        for (listArticle in listUser.userArticles!!) {
            val year = StringUtils.left(listArticle.published_at!!, 4).toInt()
            val month = StringUtils.left(listArticle.published_at!!, 7).toString()
            val finalMonth = StringUtils.right(month,2).toInt()
            if (year <= 2019 && finalMonth < 8) {
                listArticle.title?.let { listArticlePublishedBeforeAugust.add(it) }
            }
        }
    }
    for (items in listArticlePublishedBeforeAugust.distinct()) {
        println(items)
    }
}
