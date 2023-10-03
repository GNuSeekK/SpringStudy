package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class CheckedTest {

    /**
     * Exception을 상속 받은 예외는 체크 예외가 된다.
     */
    static class MyCheckedException extends Exception {
        public MyCheckedException(String message) {
            super(message);
        }
    }

    @Test
    void checked_catch() {
        Service service = new Service(new Repository());
        service.callCatch();
    }

    @Test
    void checked_throw() throws MyCheckedException {
        Service service = new Service(new Repository());
//        service.callThrow();
        Assertions.assertThatThrownBy(() -> service.callThrow())
            .isInstanceOf(MyCheckedException.class);
    }

    /**
     * 체크 예외는 반드시 try-catch로 처리해야 한다.
     * try-catch를 하지 않으면 컴파일 에러가 발생한다.
     */
    static class Service {
        private Repository repository;

        public Service(Repository repository) {
            this.repository = repository;
        }

        public void callCatch() {
            try {
                repository.call();
            } catch (MyCheckedException e) {
                log.error("예외 처리, message={}", e.getMessage(), e);
            }
        }

        public void callThrow() throws MyCheckedException {
            repository.call();
        }

    }

    static class Repository {
        public void call() throws MyCheckedException {
            throw new MyCheckedException("ex");
        }
    }

}
