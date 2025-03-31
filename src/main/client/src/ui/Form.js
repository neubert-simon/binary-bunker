import styled from "styled-components";

/**
 * The Form component is a styled form container that aligns its children horizontally,
 * with responsive behavior to adjust the spacing between elements based on the screen size.
 * It is designed to center its content, providing a uniform gap between form elements.
 *
 * @component
 * @example
 * <Form>
 *   <Input />
 *   <SubmitButton />
 * </Form>
 */
const Form = styled.form`
  position: relative;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 3rem;

  @media (max-width: 576px) {
      gap: 1rem;
  }
    
  @media (min-width: 577px) and (max-width: 768px) {
      gap: 1rem;
  }
    
  @media (min-width: 769px) and (max-width: 992px) {
       gap: 1.5rem;
  }
    
  @media (min-width: 993px) and (max-width: 1200px) {
        
  }
    
  @media (min-width: 1200px) and (max-width: 1599px) {
       
  }
`;

export default Form;
