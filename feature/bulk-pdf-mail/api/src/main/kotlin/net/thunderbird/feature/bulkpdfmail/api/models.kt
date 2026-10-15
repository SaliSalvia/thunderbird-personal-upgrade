package net.thunderbird.feature.bulkpdfmail.api

import kotlin.jvm.JvmInline

@JvmInline
value class PdfId(val value: String)

@JvmInline
value class BatchId(val value: String)

@JvmInline
value class RecipientId(val value: String)

/** Minimal representation of an app-managed PDF (metadata only). */
data class ManagedPdf(
    val id: PdfId,
    val name: String,
    val appManagedUri: String,
    val matchingKeys: List<String> = emptyList(),
    val shared: Boolean = false,
    val active: Boolean = true,
)

/** Simple representation of a message template. */
data class BulkTemplate(
    val id: String,
    val subjectTemplate: String,
    val bodyTemplate: String,
)

/** Batch and recipient models (minimal for domain rules). */
data class BulkBatch(
    val id: BatchId,
    val createdAtMillis: Long,
    val initiatedBy: String,
)

enum class RecipientState { PENDING, PREPARING, SUBMITTING, SUBMISSION_CONFIRMED, FAILED, UNCERTAIN, SKIPPED_NO_DOCUMENTS, CANCELLED }

data class BulkRecipient(
    val id: RecipientId,
    val email: String,
    val nameSuggestion: String?,
    val normalizedEmail: String,
    val state: RecipientState = RecipientState.PENDING
)

/** Persistence / repository abstraction for the feature. Implementations belong in the persistence module. */
interface BulkPdfRepository {
    suspend fun importManagedPdf(appManagedUri: String, name: String, matchingKeys: List<String>, shared: Boolean = false): ManagedPdf
    suspend fun listSharedPdfs(): List<ManagedPdf>
    suspend fun listSpecificPdfs(): List<ManagedPdf>
    suspend fun findSpecificPdfsForKey(key: String): List<ManagedPdf>

    /** Reservation API: reserve a pdf for a recipient in a batch. Returns true when reservation succeeds. */
    suspend fun reservePdfForRecipient(pdfId: PdfId, batchId: BatchId, recipientId: RecipientId): Boolean

    /** Mark the pdf as consumed (physically delete app-managed copy) when preconditions are met. Returns true if consumed. */
    suspend fun tryConsumePdfIfEligible(pdfId: PdfId): Boolean
}
