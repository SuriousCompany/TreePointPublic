package company.surious.treepoint.ui.common.fragments.photo

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoStorage(val photos: List<Uri>) : Parcelable