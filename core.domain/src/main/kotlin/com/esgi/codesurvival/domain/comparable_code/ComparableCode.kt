package com.esgi.codesurvival.domain.comparable_code

import java.security.MessageDigest

class ComparableCode (val code: String) {

    fun isSimilar(previousCode : String, threshold : Double) : Boolean {
        val similarityRate1 = getSimilarityRate(6, 4, previousCode)
        val similarityRate2 = getSimilarityRate(8, 8, previousCode)
        return ((similarityRate1 + similarityRate2) / 2) > threshold
    }

    private fun hashString(type: String, input: String): String {
        val HEX_CHARS = "0123456789ABCDEF"
        val bytes = MessageDigest
            .getInstance(type)
            .digest(input.toByteArray())
        val result = StringBuilder(bytes.size * 2)

        bytes.forEach {
            val i = it.toInt()
            result.append(HEX_CHARS[i shr 4 and 0x0f])
            result.append(HEX_CHARS[i and 0x0f])
        }
        return result.toString()
    }

    private fun extractFingerprints(content: String, k: Int): List<String> {
        val fingerprints = mutableListOf<String>()

        for (loc in 0 until (content.length - k)) {
            val kgram = content.subSequence(loc, loc + k)
            val hashedKgram = hashString("SHA-256", kgram as String)
            fingerprints.add(hashedKgram)
        }

        return fingerprints
    }

    private fun sampleFingerprints(rawFingerPrints: List<String>, w: Int): Set<String> {
        val sampledFingerprints = mutableSetOf<String>()
        var minIndex = -1
        var preMinIndex = -1

        for (i in 0 until (rawFingerPrints.size- w)) {
            var tmpMin: String? = null

            for (j in i until i+w)    {
                if (tmpMin === null || rawFingerPrints[j] <= tmpMin) {
                    minIndex = j
                    tmpMin = rawFingerPrints[j]
                }
            }

            if (minIndex != preMinIndex) {
                preMinIndex = minIndex
                sampledFingerprints.add(rawFingerPrints[minIndex])
            }
        }

        return sampledFingerprints
    }

    private fun getSimilarityRate(t: Int, k: Int, comparedContent: String): Double {
        val suspectSet = this.sampleFingerprints(this.extractFingerprints(this.code, k), t + 1 - k)
        val comparedSet = this.sampleFingerprints(this.extractFingerprints(comparedContent, k), t + 1 - k)

        if (suspectSet.isEmpty()) return 0.0

        return suspectSet.intersect(comparedSet).size.toDouble() / suspectSet.size.toDouble()
    }
}