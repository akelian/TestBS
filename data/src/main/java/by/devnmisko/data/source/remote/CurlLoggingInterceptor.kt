package by.devnmisko.data.source.remote

import android.text.TextUtils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.io.IOException
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CurlLoggingInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val startTime = System.currentTimeMillis()
        val request = chain.request()
        val url = request.url.toString()
        val curlCommandBuilder = StringBuilder()
        // add cURL command
        curlCommandBuilder.append("curl ")
        curlCommandBuilder.append("-X ")
        // add method
        curlCommandBuilder.append(request.method.uppercase(Locale.getDefault()) + " ")
        // adding headers
        for (headerName in request.headers.names()) {
            addHeader(curlCommandBuilder, headerName, request.headers[headerName])
        }

        // adding request body
        addRequestBody(request, curlCommandBuilder)

        // add request URL
        curlCommandBuilder.append(" \"").append(url).append("\"")
        curlCommandBuilder.append(" -L")

        val response = processResponse(chain, curlCommandBuilder, url, startTime)

        val responseBody = response.body
        val responseBodyString = responseBody!!.string()

        //copy response (we can read it only once)
        val responseCopy = response
            .newBuilder()
            .body(responseBodyString.toByteArray().toResponseBody(responseBody.contentType()))
            .build()
        CurlPrinter.print(
            url,
            startTime,
            System.currentTimeMillis(),
            curlCommandBuilder.toString(),
            responseBodyString
        )
        return responseCopy
    }

    private fun addRequestBody(request: Request, curlCommandBuilder: StringBuilder) {
        // adding request body
        val requestBody = request.body
        if (request.body != null) {
            val buffer = Buffer()
            requestBody!!.writeTo(buffer)
            val charset: Charset
            val contentType = requestBody.contentType()
            if (contentType != null) {
                addHeader(
                    curlCommandBuilder,
                    "Content-Type",
                    request.body!!.contentType().toString()
                )
                charset = contentType.charset(Charsets.UTF_8) ?: Charset.defaultCharset()
                var body: String? = buffer.readString(charset)
                try {
                    body = JSONObject(body.toString()).toString(4)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                curlCommandBuilder.append(" -d '").append(body).append("'")
            }
        }
    }

    private fun processResponse(
        chain: Interceptor.Chain,
        curlCommandBuilder: StringBuilder,
        url: String,
        startTime: Long
    ): Response {
        return try {
            chain.proceed(chain.request())
            //Catch and log all exceptions
        } catch (e: Exception) {
            curlCommandBuilder.append("\nError: ")
                .append(e)
            //Forward exception to OkHTTP to handle
            CurlPrinter.print(
                url,
                startTime,
                System.currentTimeMillis(),
                curlCommandBuilder.toString(),
                null
            )
            throw e
        }
    }

    private fun addHeader(
        builder: StringBuilder,
        headerName: String,
        headerValue: String?
    ) {
        builder.append("-H " + "\"").append(headerName).append(": ").append(headerValue)
            .append("\" ")
    }

    private object CurlPrinter {
        private val mDateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.ENGLISH)

        /**
         * Drawing toolbox
         */
        private const val SINGLE_DIVIDER = "────────────────────────────────────────────"
        fun print(
            url: String?,
            startTime: Long,
            endTime: Long,
            msg: String?,
            response: String?
        ) {
            val logMsg =
                StringBuilder("\n---------------- START ----------------\n")
            logMsg.append("\n")
            logMsg.append("URL: ").append(url)
            logMsg.append("\n")
            logMsg.append("Start time: ")
                .append(mDateFormat.format(Date(startTime)))
            logMsg.append("\n")
            logMsg.append("End time: ")
                .append(mDateFormat.format(Date(endTime)))
            logMsg.append("\n")
            logMsg.append(SINGLE_DIVIDER)
            logMsg.append("\n")
            logMsg.append(msg)
            logMsg.append(" ")
            logMsg.append(" \n")
            logMsg.append(SINGLE_DIVIDER)
            logMsg.append(" \n ")
            if (!TextUtils.isEmpty(response)) {
                logMsg.append("Response: ").append(response)
                logMsg.append(" \n ")
            }
            logMsg.append("---------------- END ----------------\n")
            log(logMsg.toString())
        }

        private fun log(msg: String) {
            Timber.d(msg)
        }
    }

} 