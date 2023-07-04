package subway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import subway.Station;
import subway.StationRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StationRepositoryTest {
    @Autowired
    private StationRepository stations;

    @Test
    void save() {
        subway.Station station = new subway.Station("잠실역");
        stations.save(station);

        assertThat(station.getId()).isNotNull();
        assertThat(station.getName()).isEqualTo("잠실역");
    }

    @Test
    void findByName() {
        subway.Station station = new subway.Station("잠실역");
        stations.save(station);

        subway.Station findStation = stations.findByName("잠실역");
        assertThat(findStation.getName()).isEqualTo(station.getName());
        assertThat(findStation.getId()).isEqualTo(station.getId());
        System.out.println("station = " + (station == findStation));
    }

    @Test
    void identity1(){
        subway.Station station = stations.save(new subway.Station("잠실역"));
        subway.Station findStation = stations.findById(station.getId()).get();

        assertThat(station).isSameAs(findStation);
    }

    @Test
    void identity2() {
        subway.Station station = stations.save(new subway.Station("잠실역"));
        subway.Station findStation = stations.findById(station.getId()).get();

        // name은 ID가 아니기 떄문에, select문이 두 번 나감
        stations.findByName(station.getName());
        stations.findByName(station.getName());

        assertThat(station).isSameAs(findStation);
    }

    @Test
    void update(){
        subway.Station station = stations.save(new subway.Station("잠실역"));
        station.changeName("선릉역");
        // 잠실역으로 다시 바꿀 경우에 변경 감지를 통해, update 쿼리가 나가지 않음
//        station.changeName("잠실역");

        Station findStation = stations.findByName("선릉역");

        assertThat(findStation).isNotNull();
    }
}