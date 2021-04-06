package com.lakue.pockettest

import android.app.Application
import com.lakue.pockettest.base.BaseApplication
import dagger.hilt.android.testing.CustomTestApplication
import junit.framework.TestCase

@CustomTestApplication(BaseApplication::class)
class PocketApplicationTest