import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './filiere.reducer';
import { IFiliere } from 'app/shared/model/filiere.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFiliereDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FiliereDetail = (props: IFiliereDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { filiereEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="intranetApp.filiere.detail.title">Filiere</Translate> [<b>{filiereEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="idFiliere">
              <Translate contentKey="intranetApp.filiere.idFiliere">Id Filiere</Translate>
            </span>
          </dt>
          <dd>{filiereEntity.idFiliere}</dd>
          <dt>
            <span id="nomFiliere">
              <Translate contentKey="intranetApp.filiere.nomFiliere">Nom Filiere</Translate>
            </span>
          </dt>
          <dd>{filiereEntity.nomFiliere}</dd>
          <dt>
            <span id="abreviation">
              <Translate contentKey="intranetApp.filiere.abreviation">Abreviation</Translate>
            </span>
          </dt>
          <dd>{filiereEntity.abreviation}</dd>
        </dl>
        <Button tag={Link} to="/filiere" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/filiere/${filiereEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ filiere }: IRootState) => ({
  filiereEntity: filiere.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FiliereDetail);
