package com.charmroom.charmroom.repository;

import com.charmroom.charmroom.entity.Ad;
import com.charmroom.charmroom.entity.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Ad Unit Test")
public class AdRepositoryUnitTest {
    @Autowired
    AdRepository adRepository;
    @Autowired
    ImageRepository imageRepository;

    private Ad ad;
    private Image image;

    private Image createTestImage() {
        return Image.builder()
                .path("/example/example")
                .build();
    }

    private Ad createTestAd() {
        image = createTestImage();
        imageRepository.save(image);

        return Ad.builder()
                .title("")
                .link("")
                .image(image)
                .build();
    }

    @BeforeEach
    void setUp() {
        ad = createTestAd();
    }

    @Nested
    @DisplayName("Create Ad")
    class createTestAd {
        @Test
        void success() {
            // given
            // when
            Ad saved = adRepository.save(ad);

            // then
            assertThat(saved).isNotNull();
            assertThat(saved).isEqualTo(ad);
        }
    }

    @Nested
    @DisplayName("Read Ad")
    class readTestAd {
        @Test
        void success() {
            // given
            Ad saved = adRepository.save(ad);

            // when
            Ad found = adRepository.findById(saved.getId()).get();

            // then
            assertThat(found).isNotNull();
            assertThat(found).isEqualTo(saved);
        }

        @Test
        void fail_readAdWithWrongId() {
            // given
            adRepository.save(ad);
            Integer wrongId = 999;

            // when
            Optional<Ad> found = adRepository.findById(wrongId);

            // then
            assertThat(found).isNotPresent();
            assertThat(found).isNotNull();
        }
    }

    @Nested
    @DisplayName("Update Ad")
    class updateTestAd {
        @Test
        void success() {
            // given
            Ad saved = adRepository.save(ad);
            Image testImage = Image.builder()
                    .path("/test/example")
                    .build();
            imageRepository.save(testImage);

            // when
            saved.updateTitle("test title");
            saved.updateStart(LocalDateTime.of(2023, 6, 17, 15, 30, 0));
            saved.updateEnd(LocalDateTime.of(2023, 6, 17, 15, 30, 0));
            saved.updateLink("test link");
            saved.updateImage(testImage);

            // then
            assertThat(saved).isNotNull();
            assertThat(saved.getTitle()).isEqualTo("test title");
            assertThat(saved.getStart()).isEqualTo(LocalDateTime.of(2023, 6, 17, 15, 30, 0));
            assertThat(saved.getEnd()).isEqualTo(LocalDateTime.of(2023, 6, 17, 15, 30, 0));
            assertThat(saved.getLink()).isEqualTo("test link");
            assertThat(saved.getImage()).isEqualTo(testImage);
        }
    }

    @Nested
    @DisplayName("Delete Ad")
    class deleteTestAd {
        @Test
        void success() {
            // given
            Ad saved = adRepository.save(ad);

            // when
            adRepository.delete(saved);
            Optional<Ad> found = adRepository.findById(saved.getId());

            // then
            assertThat(found).isNotPresent();
            assertThat(found).isNotNull();
        }
    }
}
