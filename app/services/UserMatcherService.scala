package services

import java.text.Normalizer
import java.util.regex.Pattern

import models.HipChatUser

class UserMatcherService
{
  def userNameLike(user: HipChatUser, searchString: String): Boolean =
  {
    val userName = normalizeString(user.name)
    val search = normalizeString(searchString)
    val mentionName = normalizeString(user.mention_name)

    userName.contains(search) || mentionName.contains(search)
  }

  private def normalizeString(string: String): String =
  {
    val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
    pattern.matcher(Normalizer.normalize(string, Normalizer.Form.NFD).toLowerCase).replaceAll("")
  }
}
