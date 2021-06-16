package hr.algebra.lol_projekt

import android.content.*
import android.database.Cursor
import android.net.Uri
import hr.algebra.lol_projekt.dao.LoLSqlHelper
import hr.algebra.lol_projekt.model.Item
import java.lang.IllegalArgumentException

private const val AUTHORITY = "hr.algebra.lol_projekt.api.provider"
private const val PATH = "items"
val LOL_PROJEKT_PROVIDER_CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$PATH")


private const val ITEMS = 10
private const val ITEM_ID = 20
private val URI_MATCHER = with(UriMatcher(UriMatcher.NO_MATCH)) {
    addURI(AUTHORITY, PATH, ITEMS)
    addURI(AUTHORITY, "$PATH/#", ITEM_ID)
    this
}
private const val CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH
private const val CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + PATH

class LoLProvider : ContentProvider() {

    private lateinit var repository : LoLSqlHelper

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        when(URI_MATCHER.match(uri)) {
            ITEMS -> return repository.delete(selection, selectionArgs)
            ITEM_ID -> {
                val id = uri.lastPathSegment // 1
                if (id != null) {
                    return repository.delete("${Item::_id.name} = ?", arrayOf(id))
                }
            }
        }
        throw IllegalArgumentException("Wrong URI")
    }

    override fun getType(uri: Uri): String? {
        when(URI_MATCHER.match(uri)) {
            ITEMS -> return CONTENT_DIR_TYPE
            ITEM_ID -> return CONTENT_ITEM_TYPE
        }
        throw IllegalArgumentException("Wrong URI")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val id = repository.insert(values)
        return ContentUris.withAppendedId(LOL_PROJEKT_PROVIDER_CONTENT_URI, id)
    }

    override fun onCreate(): Boolean {
        repository = LoLSqlHelper(context)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? = repository.query(projection, selection, selectionArgs, sortOrder)

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        when(URI_MATCHER.match(uri)){
            ITEMS -> return repository.update(values, selection, selectionArgs)
            ITEM_ID -> {
                val id = uri.lastPathSegment
                if (id != null)
                {
                    return repository.update(values,"${Item::_id.name}​= ?", arrayOf(id))
                }
            }
        }
        throw IllegalArgumentException("Wrong URI")
    }
}