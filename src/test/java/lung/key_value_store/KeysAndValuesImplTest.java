package lung.key_value_store;

import lung.key_value_store.api.ErrorListener;
import lung.key_value_store.api.KeysAndValues;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Author WAN, Kwok Lung
 */
public class KeysAndValuesImplTest {

    @Test
    public void givenExample1() {
        final ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onError(String msg) {
                Assert.fail();
            }

            @Override
            public void onError(String msg, Exception e) {
                Assert.fail();
            }
        };

        KeysAndValues kv = new KeysAndValuesImpl(errorListener);
        kv.accept("pi=314159,hello=world");
        Assert.assertEquals("hello=world\npi=314159", kv.display());
    }

    @Test
    public void givenExample2() {
        final ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onError(String msg) {
                Assert.fail();
            }

            @Override
            public void onError(String msg, Exception e) {
                Assert.fail();
            }
        };

        KeysAndValues kv = new KeysAndValuesImpl(errorListener);
        kv.accept("14=15");
        kv.accept("A=B52");
        kv.accept("dry=D.R.Y.");
        kv.accept("14=7");
        kv.accept("14=4");
        kv.accept("dry=Don't Repeat Yourself");
        Assert.assertEquals("14=26\nA=B52\ndry=Don't Repeat Yourself", kv.display());
    }

    @Test
    public void givenExample3() {
        final ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onError(String msg) {
                Assert.fail();
            }

            @Override
            public void onError(String msg, Exception e) {
                Assert.fail();
            }
        };

        KeysAndValues kv = new KeysAndValuesImpl(errorListener);
        kv.accept("14=15, 14=7,A=B52, 14 = 4, dry = Don't Repeat Yourself");
        Assert.assertEquals("14=26\nA=B52\ndry=Don't Repeat Yourself", kv.display());
    }

    @Test
    public void givenExample4() {
        final ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onError(String msg) {
                Assert.fail();
            }

            @Override
            public void onError(String msg, Exception e) {
                Assert.fail();
            }
        };

        KeysAndValues kv = new KeysAndValuesImpl(errorListener);
        kv.accept("one=two");
        kv.accept("Three=four");
        kv.accept("5=6");
        kv.accept("14=X");
        Assert.assertEquals("14=X\n5=6\none=two\nThree=four", kv.display());
    }

    @Test
    public void givenExampleForAtomicGroup1() {
        final ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onError(String msg) {
                Assert.fail();
            }

            @Override
            public void onError(String msg, Exception e) {
                Assert.fail();
            }
        };

        KeysAndValues kv = new KeysAndValuesImpl(errorListener);
        kv.accept("441=one,X=Y, 442=2,500=three");
        Assert.assertEquals("441=one\n442=2\n500=three\nX=Y", kv.display());
    }

    @Test
    public void givenExampleForAtomicGroup2() {
        final ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onError(String msg) {
                Assert.fail();
            }

            @Override
            public void onError(String msg, Exception e) {
                Assert.fail();
            }
        };

        KeysAndValues kv = new KeysAndValuesImpl(errorListener);
        kv.accept("18=zzz,441=one,500=three,442=2,442= A,441 =3,35=D,500=ok  ");
        Assert.assertEquals("18=zzz\n35=D\n441=3\n442=A\n500=ok", kv.display());
    }

    @Test
    public void givenExampleForAtomicGroup3() {
        /**
         * As a wrapper of a Boolean to be modified in the anonymous inner class.
         */
        final Boolean[] isOnErrorCalled = new Boolean[1];
        isOnErrorCalled[0] = false;

        final ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onError(String msg) {
                Assert.assertEquals("atomic group(441,442,500) missing 442", msg);
                isOnErrorCalled[0] = true;
            }

            @Override
            public void onError(String msg, Exception e) {
                Assert.fail();
            }
        };

        KeysAndValues kv = new KeysAndValuesImpl(errorListener);
        kv.accept("441=3,500=not ok,13=qwerty");
        Assert.assertEquals("13=qwerty", kv.display());

        /**
         * To make sure ErrorListener.onError(String) has been called.
         */
        Assert.assertTrue(isOnErrorCalled[0]);
    }

    @Test
    public void givenExampleForAtomicGroup4() {
        /**
         * As a wrapper of a Boolean to be modified in the anonymous inner class.
         */
        final Boolean[] isOnErrorCalled = new Boolean[1];
        isOnErrorCalled[0] = false;

        final ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onError(String msg) {
                Assert.assertEquals("atomic group(441,442,500) missing 441,500", msg);
                isOnErrorCalled[0] = true;

            }

            @Override
            public void onError(String msg, Exception e) {
                Assert.fail();
            }
        };

        KeysAndValues kv = new KeysAndValuesImpl(errorListener);
        kv.accept("500= three , 6 = 7 ,441= one,442=1,442=4");
        Assert.assertEquals("441=one\n442=1\n500=three\n6=7", kv.display());

        /**
         * To make sure ErrorListener.onError(String) has been called.
         */
        Assert.assertTrue(isOnErrorCalled[0]);
    }

    @Test
    public void customTest1() {
        final ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onError(String msg) {
                Assert.fail();
            }

            @Override
            public void onError(String msg, Exception e) {
                Assert.fail();
            }
        };

        KeysAndValues kv = new KeysAndValuesImpl(errorListener);
        kv.accept("1=1");
        kv.accept("1=2,1=3");
        kv.accept("A=A    ,    A   =  C");
        kv.accept("A = B");
        kv.accept("B  =  1");
        kv.accept("B =  B");
        kv.accept(" C=C ");
        kv.accept("   C=1   ");
        kv.accept("C=2");
        Assert.assertEquals("1=6\nA=B\nB=B\nC=3", kv.display());
    }

    @Test
    public void customTestNotCallingAccept() {
        final ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onError(String msg) {
                Assert.fail();
            }

            @Override
            public void onError(String msg, Exception e) {
                Assert.fail();
            }
        };

        KeysAndValues kv = new KeysAndValuesImpl(errorListener);
        Assert.assertEquals("", kv.display());
    }

    @Test
    public void customTestAcceptingEmpty() {
        final ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onError(String msg) {
                Assert.fail();
            }

            @Override
            public void onError(String msg, Exception e) {
                Assert.fail();
            }
        };

        KeysAndValues kv = new KeysAndValuesImpl(errorListener);
        kv.accept("");
        Assert.assertEquals("", kv.display());
    }

    @Test
    public void customTestInvalidFormat() {
        /**
         * As a wrapper of a Boolean to be modified in the anonymous inner class.
         */
        final Boolean[] isOnErrorCalled = new Boolean[1];
        isOnErrorCalled[0] = false;

        final ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onError(String msg) {
                Assert.assertEquals("Invalid format.", msg);
                isOnErrorCalled[0] = true;

            }

            @Override
            public void onError(String msg, Exception e) {
                Assert.fail();
            }
        };

        KeysAndValues kv = new KeysAndValuesImpl(errorListener);
        kv.accept("abc");
        Assert.assertEquals("", kv.display());

        /**
         * To make sure ErrorListener.onError(String) has been called.
         */
        Assert.assertTrue(isOnErrorCalled[0]);
    }

    @Test
    public void customTestInvalidFormat2() {
        /**
         * As a wrapper of a Boolean to be modified in the anonymous inner class.
         */
        final Boolean[] isOnErrorCalled = new Boolean[1];
        isOnErrorCalled[0] = false;

        final ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onError(String msg) {
                Assert.assertEquals("Invalid format.", msg);
                isOnErrorCalled[0] = true;

            }

            @Override
            public void onError(String msg, Exception e) {
                Assert.fail();
            }
        };

        KeysAndValues kv = new KeysAndValuesImpl(errorListener);
        kv.accept("abc==1");
        Assert.assertEquals("", kv.display());

        /**
         * To make sure ErrorListener.onError(String) has been called.
         */
        Assert.assertTrue(isOnErrorCalled[0]);
    }

    @Test
    public void customTestIntegerValueOverflows() {
        /**
         * As a wrapper of a Boolean to be modified in the anonymous inner class.
         */
        final Boolean[] isOnErrorCalled = new Boolean[1];
        isOnErrorCalled[0] = false;

        final ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onError(String msg) {
                Assert.fail();
            }

            @Override
            public void onError(String msg, Exception e) {
                Assert.assertEquals("The integer value overflows.", msg);
                Assert.assertNotNull(e);
                isOnErrorCalled[0] = true;

            }
        };

        KeysAndValues kv = new KeysAndValuesImpl(errorListener);
        kv.accept("A=0,A=" + Integer.MAX_VALUE + "0");
        Assert.assertEquals("A=0", kv.display());

        /**
         * To make sure ErrorListener.onError(String,Exception) has been called.
         */
        Assert.assertTrue(isOnErrorCalled[0]);
    }

    @Test
    public void customTestForAtomicGroup1() {
        /**
         * As a wrapper of a Boolean to be modified in the anonymous inner class.
         */
        final Boolean[] isOnErrorCalled = new Boolean[1];
        isOnErrorCalled[0] = false;

        final ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onError(String msg) {
                Assert.assertEquals("atomic group(441,442,500) missing 441", msg);
                isOnErrorCalled[0] = true;

            }

            @Override
            public void onError(String msg, Exception e) {
                Assert.fail();
            }
        };

        KeysAndValues kv = new KeysAndValuesImpl(errorListener);
        kv.accept("442=1,500=1");
        Assert.assertEquals("", kv.display());

        /**
         * To make sure ErrorListener.onError(String) has been called.
         */
        Assert.assertTrue(isOnErrorCalled[0]);
    }

    @Test
    public void customTestForAtomicGroup2() {
        /**
         * As a wrapper of a Boolean to be modified in the anonymous inner class.
         */
        final Boolean[] isOnErrorCalled = new Boolean[1];
        isOnErrorCalled[0] = false;

        final ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onError(String msg) {
                Assert.assertEquals("atomic group(441,442,500) missing 442", msg);
                isOnErrorCalled[0] = true;

            }

            @Override
            public void onError(String msg, Exception e) {
                Assert.fail();
            }
        };

        KeysAndValues kv = new KeysAndValuesImpl(errorListener);
        kv.accept("441=1,500=1");
        Assert.assertEquals("", kv.display());

        /**
         * To make sure ErrorListener.onError(String) has been called.
         */
        Assert.assertTrue(isOnErrorCalled[0]);
    }

    @Test
    public void customTestForAtomicGroup3() {
        /**
         * As a wrapper of a Boolean to be modified in the anonymous inner class.
         */
        final Boolean[] isOnErrorCalled = new Boolean[1];
        isOnErrorCalled[0] = false;

        final ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onError(String msg) {
                Assert.assertEquals("atomic group(441,442,500) missing 500", msg);
                isOnErrorCalled[0] = true;

            }

            @Override
            public void onError(String msg, Exception e) {
                Assert.fail();
            }
        };

        KeysAndValues kv = new KeysAndValuesImpl(errorListener);
        kv.accept("441=1,442=1");
        Assert.assertEquals("", kv.display());

        /**
         * To make sure ErrorListener.onError(String) has been called.
         */
        Assert.assertTrue(isOnErrorCalled[0]);
    }

    @Test
    public void customTestForAtomicGroup4() {
        /**
         * As a wrapper of a Boolean to be modified in the anonymous inner class.
         */
        final Boolean[] isOnErrorCalled = new Boolean[1];
        isOnErrorCalled[0] = false;

        final ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onError(String msg) {
                Assert.assertEquals("atomic group(441,442,500) missing 441,442", msg);
                isOnErrorCalled[0] = true;

            }

            @Override
            public void onError(String msg, Exception e) {
                Assert.fail();
            }
        };

        KeysAndValues kv = new KeysAndValuesImpl(errorListener);
        kv.accept("500=1");
        Assert.assertEquals("", kv.display());

        /**
         * To make sure ErrorListener.onError(String) has been called.
         */
        Assert.assertTrue(isOnErrorCalled[0]);
    }

    @Test
    public void customTestForAtomicGroup5() {
        /**
         * As a wrapper of a Boolean to be modified in the anonymous inner class.
         */
        final Boolean[] isOnErrorCalled = new Boolean[1];
        isOnErrorCalled[0] = false;

        final ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onError(String msg) {
                Assert.assertEquals("atomic group(441,442,500) missing 441,500", msg);
                isOnErrorCalled[0] = true;

            }

            @Override
            public void onError(String msg, Exception e) {
                Assert.fail();
            }
        };

        KeysAndValues kv = new KeysAndValuesImpl(errorListener);
        kv.accept("442=1");
        Assert.assertEquals("", kv.display());

        /**
         * To make sure ErrorListener.onError(String) has been called.
         */
        Assert.assertTrue(isOnErrorCalled[0]);
    }

    @Test
    public void customTestForAtomicGroup6() {
        /**
         * As a wrapper of a Boolean to be modified in the anonymous inner class.
         */
        final Boolean[] isOnErrorCalled = new Boolean[1];
        isOnErrorCalled[0] = false;

        final ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onError(String msg) {
                Assert.assertEquals("atomic group(441,442,500) missing 442,500", msg);
                isOnErrorCalled[0] = true;

            }

            @Override
            public void onError(String msg, Exception e) {
                Assert.fail();
            }
        };

        KeysAndValues kv = new KeysAndValuesImpl(errorListener);
        kv.accept("441=1");
        Assert.assertEquals("", kv.display());

        /**
         * To make sure ErrorListener.onError(String) has been called.
         */
        Assert.assertTrue(isOnErrorCalled[0]);
    }
}