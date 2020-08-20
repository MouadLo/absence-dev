package com.miage.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.miage.app.web.rest.TestUtil;

public class ProfTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prof.class);
        Prof prof1 = new Prof();
        prof1.setId(1L);
        Prof prof2 = new Prof();
        prof2.setId(prof1.getId());
        assertThat(prof1).isEqualTo(prof2);
        prof2.setId(2L);
        assertThat(prof1).isNotEqualTo(prof2);
        prof1.setId(null);
        assertThat(prof1).isNotEqualTo(prof2);
    }
}
