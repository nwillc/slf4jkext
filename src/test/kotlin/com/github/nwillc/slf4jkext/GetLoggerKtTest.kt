/*
 * Copyright (c) 2020, nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 */

package com.github.nwillc.ksvg

import com.github.nwillc.slf4jkext.getLogger
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.slf4j.Logger

class GetLoggerKtTest {
    @Test
    fun `return an appropriate logger from reified type`() {
        val logger = getLogger<GetLoggerKtTest>()

        assertThat(logger).isInstanceOf(Logger::class.java)
        assertThat(logger.name!!).isEqualTo(GetLoggerKtTest::class.java.name)
    }

    @Test
    fun `return an appropriate logger from name`() {
        val logger = getLogger("foo")

        assertThat(logger).isInstanceOf(Logger::class.java)
        assertThat(logger.name!!).isEqualTo("foo")
    }

    @Test
    fun `return an appropriate logger from class`() {
        val logger = getLogger(String::class.java)

        assertThat(logger).isInstanceOf(Logger::class.java)
        assertThat(logger.name!!).isEqualTo(String::class.java.name)
    }
}
