package hello.jdbc.exception.basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class UncheckedTest {

    @Test
    void 언체크_잡기() {
        Service service = new Service(new Repository());
        service.callCatch();
    }

    @Test
    void 언체크_던지기() {
        Service service = new Service(new Repository());
        Assertions.assertThatThrownBy(() -> service.callThrow())
            .isInstanceOf(MyUnchecedException.class);
    }

    static class MyUnchecedException extends RuntimeException{
        public MyUnchecedException(String message) {
            super(message);
        }
    }

    static class Repository {

        /**
         * RuntimeException을 상속 받은 예외는 언체크 예외가 된다.
         * 언체크 예외는 반드시 try-catch로 처리하지 않아도 된다.
         */
        public void call() {
            throw new MyUnchecedException("ex");
        }
    }

    static class Service {
        private Repository repository;

        public Service(Repository repository) {
            this.repository = repository;
        }

        public void callCatch() {
            try {
                repository.call();
            } catch (MyUnchecedException e) {
                System.out.println("예외 처리, message={}" + e.getMessage());
            }
        }

        public void callThrow() {
            repository.call();
        }

    }

}
