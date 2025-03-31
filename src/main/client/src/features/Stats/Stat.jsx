import styled, { css } from "styled-components";

const variations = {
  TopRightBottomRight: css`
    border-top-right-radius: var(--border-radius-md);
    border-bottom-right-radius: var(--border-radius-md);
  `,
  TopLeftBottomLeft: css`
    border-top-left-radius: var(--border-radius-md);
    border-bottom-left-radius: var(--border-radius-md);
  `,
};

const outlinevariations = {
  top: css`
    top: -0.5rem;
    left: 0;
    right: 0;
    bottom: 0.5rem;
  `,
  right: css`
    top: 0;
    left: 0.5rem;
    right: -0.5rem;
    bottom: 0;
  `,
  bottom: css`
    top: 0.8rem;
    left: 0.5rem;
    right: -0.3rem;
    bottom: -0.8rem;
  `,
  left: css`
    top: -0.5rem;
    left: -0.5rem;
    right: 0.5rem;
    bottom: 0.5rem;
  `,
};

const StyledStat = styled.div`
  position: relative;
  width: 15vw;
  height: 15vw;
  background-color: var(--color-gray);
  color: var(--color-white);
  font-family: aubrey;
  line-height: 1;
  border-radius: var(--border-radius-sm);
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  gap: 1rem;
  transition: all 0.3s ease-out;

  ${(props) => variations[props.variation]}

  &:hover {
    background-color: var(--color-blue);
  }

  &::before {
    content: "";
    position: absolute;
    z-index: 2;
    outline: 0.15rem solid var(--color-white);
    border-radius: var(--border-radius-sm);
    ${(props) => variations[props.variation]};
    ${(props) => outlinevariations[props.outlinevariation]};
    transition: all 0.3s ease-out;
  }

  &:hover::before {
    transform: scale(1.03);
  }

  .label {
    font-size: 2.5rem;
    text-transform: uppercase;
    text-align: center;
  }

  .statvalue {
    font-size: 7rem;
    font-family: impact;
  }

  @media (max-width: 576px) {
    width: 10rem;
    height: 10rem;
    line-height: 1;
    border-radius: var(--border-radius-sm);

    &::before {
        border-radius: var(--border-radius-sm);
    }
    
    .label {
      font-size: 1rem;
    }

    .statvalue {
      font-size: 2rem;
    }
  }

  @media (min-width: 577px) and (max-width: 768px) {
    width: 13rem;
    height: 13rem;
    line-height: 1;
      
    .label {
      font-size: 1.3rem;
    }

    .statvalue {
      font-size: 3rem;
    }
  }

  @media (min-width: 769px) and (max-width: 992px) {
    width: 15rem;
    height: 15rem;

    .label {
      font-size: 1.3rem;
    }

    .statvalue {
      font-size: 4rem;
    }
  }

  @media (min-width: 993px) and (max-width: 1200px) {
    width: 18rem;
    height: 18rem;

    .label {
      font-size: 1.8rem;
    }

    .statvalue {
      font-size: 5rem;
    }
  }

  @media (min-width: 1200px) and (max-width: 1599px) {
    width: 23rem;
    height: 23rem;

    .label {
      font-size: 2.5rem;
    }

    .statvalue {
      font-size: 6rem;
    }
  }

  @media (orientation: landscape) and (max-height: 576px) {
    width: 10rem;
    height: 10rem;
    line-height: 1;
    border-radius: var(--border-radius-sm);

    &::before {
      border-radius: var(--border-radius-sm);
    }

    .label {
      font-size: 1rem;
    }

    .statvalue {
      font-size: 2rem;
    }
  }
`;

function Stat({ variation, outlinevariation, label, statvalue }) {
  return (
    <StyledStat variation={variation} outlinevariation={outlinevariation}>
      <span className="label">{label}</span>
      <span className="statvalue">{statvalue}</span>
    </StyledStat>
  );
}

export default Stat;
