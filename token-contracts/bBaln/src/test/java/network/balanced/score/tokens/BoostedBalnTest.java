/*
 * Copyright (c) 2021-2021 Balanced.network.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package network.balanced.score.tokens;

import com.iconloop.score.test.Account;
import com.iconloop.score.test.Score;
import com.iconloop.score.test.ServiceManager;
import com.iconloop.score.token.irc2.IRC2Basic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.iconloop.score.test.TestBase;
import score.Context;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BoostedBalnTest extends TestBase {
    private static final ServiceManager sm = getServiceManager();
    private static final Account owner = sm.createAccount();
    private Score bBalnScore;
    private Score tokenScore;

    private static final String name = "Balance Token";
    private static final String symbol = "BALN";
    private static final int decimals = 18;
    private static final BigInteger initialSupply = BigInteger.TEN.pow(21);

    private static final String bBalnName = "Boosted Balance";
    private static final String bBalnSymbol = "bBALN";

    public static class IRC2BasicToken extends IRC2Basic {
        public IRC2BasicToken(String _name, String _symbol, int _decimals, BigInteger _totalSupply) {
            super(_name, _symbol, _decimals);
            _mint(Context.getCaller(), _totalSupply);
        }
    }

    @BeforeEach
    public void setup() throws Exception {
        tokenScore = sm.deploy(owner, IRC2BasicToken.class, name, symbol, decimals, initialSupply);
        bBalnScore = sm.deploy(owner, BoostedBaln.class, tokenScore.getAddress(), bBalnName, bBalnSymbol);
    }

    @Test
    void name() {
        assertEquals(bBalnName, bBalnScore.call("name"));
    }

    @Test
    void admin() {
        assertEquals(owner.getAddress(), bBalnScore.call("admin"));
    }

    @Test
    void futureAdmin() {
        assertNull(bBalnScore.call("futureAdmin"));
    }

    @Test
    void symbol() {
        assertEquals(bBalnSymbol, bBalnScore.call("symbol"));
    }

    @Test
    void decimals() {
        assertEquals(decimals, bBalnScore.call("decimals"));
    }

    @Test
    void totalSupply() {
        assertEquals(BigInteger.ZERO, bBalnScore.call("totalSupply", BigInteger.ZERO));
    }

}